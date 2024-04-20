package task_tracker.controller;

import task_tracker.dto.AttachmentDto;
import task_tracker.enums.ContractType;
import task_tracker.service.DocumentService;
import task_tracker.utils.Result;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/document")
public class DocumentController {
    private final DocumentService documentService;

    @GetMapping("/")
    public String getHome() {
        return "document/index";
    }

    @GetMapping("/{id}")
    public void download(@PathVariable("id") Integer id, HttpServletResponse response) throws IOException {
        Result<AttachmentDto> documentRead = documentService.findById(id);
        if (documentRead.isError()) {
            return;
        }
        AttachmentDto document = documentRead.getObject();

        Result<byte[]> byteArray = documentService.getFile(document.getPath());
        if (byteArray.isError()) {
            return;
        }
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        response.setHeader("Content-Disposition", "attachment; filename=" + document.getName());
        response.setContentLength(byteArray.getObject().length);

        try (OutputStream os = response.getOutputStream()) {
            os.write(byteArray.getObject(), 0, byteArray.getObject().length);
        }
    }

    @GetMapping("/findById/{id}")
    public String findById(@PathVariable Integer id, Model model) {
        Result<AttachmentDto> document = documentService.findById(id);
        if (document.isError()) {
            model.addAttribute("message", document.getMessage());
            return document.getCode();
        } else
            model.addAttribute("document", document.getObject());
        return "document/show";
    }

    @GetMapping("/findByEmployeeId/{id}")
    public String findByEmployeeId(@PathVariable Integer id, Model model) {
        Result<List<AttachmentDto>> documentList = documentService.findByEmployeeId(id);
        if (documentList.isError()) {
            model.addAttribute("message", documentList.getMessage());
            return documentList.getCode();
        } else
            model.addAttribute("documentList", documentList.getObject());
        return "document/showList";
    }

    @GetMapping("/findByName/{name}")
    public String findByName(@PathVariable String name, Model model) {
        Result<List<AttachmentDto>> documentList = documentService.findByName(name);
        if (documentList.isError()) {
            model.addAttribute("message", documentList.getMessage());
            return documentList.getCode();
        } else
            model.addAttribute("documentList", documentList.getObject());
        return "document/showList";
    }

    @GetMapping("/findByCreationDateAfter/{date}")
    public String findByCreationDateAfter(@PathVariable LocalDateTime date, Model model) {
        Result<List<AttachmentDto>> documentList = documentService.findByCreationDateAfter(date);
        if (documentList.isError()) {
            model.addAttribute("message", documentList.getMessage());
            return documentList.getCode();
        } else
            model.addAttribute("documentList", documentList.getObject());
        return "document/showList";
    }

    @GetMapping("/findByCreationDateBefore/{date}")
    public String findByCreationDateBefore(@PathVariable LocalDateTime date, Model model) {
        Result<List<AttachmentDto>> documentList = documentService.findByCreationDateBefore(date);
        if (documentList.isError()) {
            model.addAttribute("message", documentList.getMessage());
            return documentList.getCode();
        } else
            model.addAttribute("documentList", documentList.getObject());
        return "document/showList";
    }

    @GetMapping("/upload/{id}")
    public String getNew(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("id", id);
        return "document/upload";
    }


    @GetMapping("/update/{id}")
    public String getUpdateForm(@PathVariable("id") Integer id, Model model) {
        Result<AttachmentDto> documentRead = documentService.findById(id);
        if (documentRead.isError()) {
            model.addAttribute("message", documentRead.getMessage());
            return documentRead.getCode();
        }
        model.addAttribute("document", documentRead.getObject());
        model.addAttribute("types", ContractType.values());
        return "document/update";
    }

    @PostMapping("/")
    public String create(@ModelAttribute("file") MultipartFile file, @ModelAttribute("id") Integer id, Model model) {
        Result<String> upload = documentService.upload(file, id);
        if (upload.isError()) {
            model.addAttribute("message", upload.getMessage());
            return upload.getCode();
        } else
            return "redirect:/employee/" + id;
    }

    @PostMapping("/submit")
    public String inputSubmit(
            @RequestParam(required = false, name = "string") String string,
            @RequestParam(required = false, name = "number") Integer number,
            @RequestParam(required = false, name = "employeeId") Integer employeeId,
            @RequestParam(required = false, name = "dateBefore") LocalDateTime dateBefore,
            @RequestParam(required = false, name = "dateAfter") LocalDateTime dateAfter
    ) {
        if (string != null)
            return "redirect:/document/findByName/" + string;
        if (number != null)
            return "redirect:/document/findById/" + number;
        if (employeeId != null)
            return "redirect:/document/findByEmployeeId/" + employeeId;
        if (dateBefore != null)
            return "redirect:/document/findByCreationDateBefore/" + dateBefore;
        if (dateAfter != null)
            return "redirect:/document/findByCreationDateAfter/" + dateAfter;

        return "redirect:/document/";
    }

    @PutMapping("/{id}")
    public String update(@ModelAttribute("document") AttachmentDto document, @PathVariable("id") Integer id, Model model) {
        Result<String> upload = documentService.update(id, document);
        if (upload.isError()) {
            model.addAttribute("message", upload.getMessage());
            return upload.getCode();
        } else
            return "redirect:/document/findById/" + id;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Integer id, Model model) {
        Result<String> delete = documentService.delete(id);
        if (delete.isError()) {
            model.addAttribute("message", delete.getMessage());
            return delete.getCode();
        } else
            return "redirect:/document/";
    }

}
