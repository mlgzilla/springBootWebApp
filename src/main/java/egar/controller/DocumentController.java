package egar.controller;

import egar.domain.document.dto.DocumentDtoRead;
import egar.service.DocumentService;
import egar.utils.Result;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/document")
public class DocumentController {
    private final DocumentService documentService;

//    @GetMapping("/{id}")
//    public String findById(@PathVariable("id") Integer id, Model model) {
//        Result<DocumentDtoRead> documentRead = documentService.findById(id);
//        if (documentRead.isError()) {
//            model.addAttribute("message", documentRead.getMessage());
//            return documentRead.getCode();
//        } else
//            model.addAttribute("path", documentRead.getObject().getPath());
//        return "document/show";
//    }

    @GetMapping("/{id}")
    public void findById(@PathVariable("id") Integer id, HttpServletResponse response) throws IOException {
        Result<DocumentDtoRead> documentRead = documentService.findById(id);
        if (documentRead.isError()) {
            return;
        }
        DocumentDtoRead document = documentRead.getObject();

        Result<byte[]> byteArray = documentService.getFile(document.getPath());
        if(byteArray.isError()){
            return;
        }

        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        response.setHeader("Content-Disposition", "attachment; filename=" + document.getName());
        response.setContentLength(byteArray.getObject().length);

        try (OutputStream os = response.getOutputStream()) {
            os.write(byteArray.getObject(), 0, byteArray.getObject().length);
        }
    }

    @GetMapping("/findByName/{name}")
    public String findByName(@PathVariable String name, Model model) {
        Result<List<DocumentDtoRead>> documentList = documentService.findByName(name);
        if (documentList.isError()) {
            model.addAttribute("message", documentList.getMessage());
            return documentList.getCode();
        } else
            model.addAttribute("documentList", documentList.getObject());
        return "document/showList";
    }

    @GetMapping("/findByCreationDateAfter/{date}")
    public String findByCreationDateAfter(@PathVariable LocalDateTime date, Model model) {
        Result<List<DocumentDtoRead>> documentList = documentService.findByCreationDateAfter(date);
        if (documentList.isError()) {
            model.addAttribute("message", documentList.getMessage());
            return documentList.getCode();
        } else
            model.addAttribute("documentList", documentList.getObject());
        return "document/showList";
    }

    @GetMapping("/findByCreationDateBefore/{date}")
    public String findByCreationDateBefore(@PathVariable LocalDateTime date, Model model) {
        Result<List<DocumentDtoRead>> documentList = documentService.findByCreationDateBefore(date);
        if (documentList.isError()) {
            model.addAttribute("message", documentList.getMessage());
            return documentList.getCode();
        } else
            model.addAttribute("documentList", documentList.getObject());
        return "document/showList";
    }

    @GetMapping("/upload")
    public String getNew() {
        return "document/upload";
    }

    @PostMapping("/")
    public String create(@ModelAttribute("file") MultipartFile file, Model model) {
        Result<String> upload = documentService.upload(file);
        if (upload.isError()) {
            model.addAttribute("message", upload.getMessage());
            return upload.getCode();
        } else
            return "200";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Integer id, Model model) {
        Result<String> delete = documentService.delete(id);
        if (delete.isError()) {
            model.addAttribute("message", delete.getMessage());
            return delete.getCode();
        } else
            return "200";
    }

}
