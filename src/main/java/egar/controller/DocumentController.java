package egar.controller;

import egar.domain.document.dto.DocumentDtoRead;
import egar.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/document")
public class DocumentController {
    private final DocumentService documentService;

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Integer id, Model model) {
        Optional<DocumentDtoRead> documentRead = documentService.findById(id);
        if (documentRead.isEmpty())
            return "404";
        model.addAttribute("path", documentRead.get().getPath());
        return "document/show";
    }

    @GetMapping("/upload")
    public String getNew(Model model) {
        return "document/upload";
    }

    @PostMapping("/")
    public String create(@ModelAttribute("file") MultipartFile file) {
        if (documentService.upload(file).isEmpty())
            return "500";
        return "200";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Integer id) {
        if (documentService.delete(id).isEmpty())
            return "500";
        return "200";
    }

}
