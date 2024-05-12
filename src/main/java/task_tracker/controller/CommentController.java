package task_tracker.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import task_tracker.domain.Comment;
import task_tracker.dto.CommentDto;
import task_tracker.service.CommentService;
import task_tracker.utils.Result;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") UUID id, Model model) {
        Result<CommentDto> commentRead = commentService.findById(id);
        if (commentRead.isError()) {
            model.addAttribute("message", commentRead.getMessage());
            return commentRead.getCode();
        } else
            model.addAttribute("comment", commentRead.getObject());
        return "comment/show";
    }

    @GetMapping("/new")
    public String getNew(Model model) {
        model.addAttribute(new Comment());
        return "comment/new";
    }

    @GetMapping("/update/{id}")
    public String getUpdateForm(@PathVariable("id") UUID id, Model model) {
        Result<CommentDto> commentRead = commentService.findById(id);
        if (commentRead.isError()) {
            model.addAttribute("message", commentRead.getMessage());
            return commentRead.getCode();
        }
        model.addAttribute("comment", commentRead.getObject());
        return "comment/update";
    }

    @GetMapping("/")
    public String getHome() {
        return "comment/index";
    }

    @PostMapping("/")
    public String create(@ModelAttribute("comment") Comment comment, Model model) {
        
        comment.setId(UUID.randomUUID());
        comment.setDate(LocalDateTime.now());
        Result<CommentDto> savedComment = commentService.create(comment);
        if (savedComment.isError()) {
            model.addAttribute("message", savedComment.getMessage());
            return savedComment.getCode();
        } else
            return "redirect:/task/" + savedComment.getObject().getTaskId();
    }

    @PostMapping("/submit")
    public String inputSubmit(
            @RequestParam(required = false, name = "id") UUID id,
            @RequestParam(required = false, name = "employeeId") UUID employeeId,
            @RequestParam(required = false, name = "inDateRange") boolean inDateRange,
            @RequestParam(required = false, name = "dateStart") LocalDateTime dateStart,
            @RequestParam(required = false, name = "dateFinish") LocalDateTime dateFinish
    ) {
        if (id != null)
            return "redirect:/comment/" + id;
        if (employeeId != null) {
            if (inDateRange)
                return "redirect:/comment/findByUserIdInRange/" + employeeId + "/" + dateStart + "/" + dateFinish;
            else
                return "redirect:/comment/findByUserId/" + employeeId;
        }
        return "redirect:/comment/";
    }

    @PutMapping("/{id}")
    public String update(@ModelAttribute("newContents") String newContents, @PathVariable("id") UUID id, Model model) {
        Result<String> upload = commentService.update(id, newContents);
        if (upload.isError()) {
            model.addAttribute("message", upload.getMessage());
            return upload.getCode();
        } else
            return "redirect:/comment/" + id;
    }

    @DeleteMapping("/{id}/{taskId}")
    public String delete(@PathVariable("id") UUID id, @PathVariable("taskId") UUID taskId, Model model) {
        Result<String> delete = commentService.delete(id);
        if (delete.isError()) {
            model.addAttribute("message", delete.getMessage());
            return delete.getCode();
        } else {
            model.addAttribute("message", delete.getObject());
            return "redirect:/task/" + taskId;
        }
    }
}
