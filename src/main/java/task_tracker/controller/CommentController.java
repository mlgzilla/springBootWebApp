package task_tracker.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import task_tracker.domain.Comment;
import task_tracker.dto.CommentDto;
import task_tracker.service.CommentService;
import task_tracker.utils.Result;

import java.time.LocalDateTime;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;

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
