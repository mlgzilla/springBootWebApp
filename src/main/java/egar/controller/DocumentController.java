package egar.controller;

import egar.domain.document.dto.DocumentDtoRead;
import egar.service.DocumentService;
import egar.utils.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/document")
public class DocumentController {
    private final DocumentService documentService;

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Integer id, Model model) {
        Result<DocumentDtoRead> documentRead = documentService.findById(id);
        if (documentRead.isError()) {
            model.addAttribute("message", documentRead.getMessage());
            return documentRead.getCode();
        } else
            model.addAttribute("path", documentRead.getObject().getPath());
        return "document/show";
    }

    @GetMapping("/findByName/{name}")
    public String findByName(@PathVariable String name, Model model) {
        Result<List<DocumentDtoRead>> documentList = documentService.findByName(name);
        if (documentList.isError()) {
            model.addAttribute("message", documentList.getMessage());
            return documentList.getCode();
        } else
            model.addAttribute("documentList", documentList);
        return "document/showList";
    }

    @GetMapping("/findByCreationDateAfter/{date}")
    public String findByCreationDateAfter(@PathVariable LocalDateTime date, Model model) {
        Result<List<DocumentDtoRead>> documentList = documentService.findByCreationDateAfter(date);
        if (documentList.isError()) {
            model.addAttribute("message", documentList.getMessage());
            return documentList.getCode();
        } else
            model.addAttribute("documentList", documentList);
        return "document/showList";
    }

    @GetMapping("/findByCreationDateBefore/{date}")
    public String findByCreationDateBefore(@PathVariable LocalDateTime date, Model model) {
        Result<List<DocumentDtoRead>> documentList = documentService.findByCreationDateBefore(date);
        if (documentList.isError()) {
            model.addAttribute("message", documentList.getMessage());
            return documentList.getCode();
        } else
            model.addAttribute("documentList", documentList);
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
