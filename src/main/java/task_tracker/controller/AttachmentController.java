package task_tracker.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import task_tracker.dto.AttachmentDto;
import task_tracker.service.AttachmentService;
import task_tracker.utils.Result;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/attachment")

public class AttachmentController {
    private final AttachmentService attachmentService;

    @GetMapping("/{id}")
    public void download(@PathVariable("id") UUID id, HttpServletResponse response) throws IOException {
        Result<AttachmentDto> attachmentRead = attachmentService.findById(id);
        if (attachmentRead.isError()) {
            return;
        }
        AttachmentDto attachment = attachmentRead.getObject();

        Result<byte[]> byteArray = attachmentService.getFile(attachment.getPath());
        if (byteArray.isError()) {
            return;
        }
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        response.setHeader("Content-Disposition", "attachment; filename=" + attachment.getName());
        response.setContentLength(byteArray.getObject().length);

        try (OutputStream os = response.getOutputStream()) {
            os.write(byteArray.getObject(), 0, byteArray.getObject().length);
        }
    }

    @GetMapping("/findByTaskId/{taskId}")
    public String findByTaskId(@PathVariable UUID taskId, Model model) {
        Result<List<AttachmentDto>> attachmentList = attachmentService.findByTaskId(taskId);
        if (attachmentList.isError()) {
            model.addAttribute("message", attachmentList.getMessage());
            return attachmentList.getCode();
        } else
            model.addAttribute("attachmentList", attachmentList.getObject());
        return "attachment/showList";
    }

    @GetMapping("/upload/{id}")
    public String getNew(@PathVariable("id") UUID id, Model model) {
        model.addAttribute("id", id);
        return "attachment/upload";
    }

    @PostMapping("/")
    public String create(@ModelAttribute("file") MultipartFile file, @ModelAttribute("id") UUID userId, @ModelAttribute("id") UUID taskId, Model model) {
        Result<String> upload = attachmentService.upload(file, userId, taskId);
        if (upload.isError()) {
            model.addAttribute("message", upload.getMessage());
            return upload.getCode();
        } else
            return "redirect:/user/"; //TODO
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") UUID id, Model model) {
        Result<String> delete = attachmentService.delete(id);
        if (delete.isError()) {
            model.addAttribute("message", delete.getMessage());
            return delete.getCode();
        } else
            return "redirect:/attachment/"; //TODO
    }

}
