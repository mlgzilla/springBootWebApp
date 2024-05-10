package task_tracker.service;

import org.springframework.stereotype.Service;
import task_tracker.domain.Comment;
import task_tracker.dto.CommentDto;
import task_tracker.repository.CommentRepository;
import task_tracker.utils.Result;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    public Result<CommentDto> findById(UUID id) {
        try {
            Optional<Comment> comment = commentRepository.findById(id);
            if (comment.isEmpty())
                return Result.error("Comments by task id were not found", "404");
            else
                return Result.ok(comment.get().mapToDto());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Result.error("Error finding Comments by task id", "500");
        }
    }

    public Result<List<CommentDto>> findByTaskId(UUID id) {
        try {
            List<Comment> comments = commentRepository.findByTaskId(id);
            if (comments.isEmpty())
                return Result.error("Comments by task id were not found", "404");
            else
                return Result.ok(comments.stream().map(Comment::mapToDto).collect(Collectors.toList()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Result.error("Error finding Comments by task id", "500");
        }
    }

    public Result<CommentDto> create(Comment comment) {
        try {
            Comment savedComment = commentRepository.saveAndFlush(comment);
            return Result.ok(savedComment.mapToDto());
        } catch (Exception e) {
            return Result.error(e.getMessage() + "Error creating Comment", "500");
        }
    }

    public Result<String> update(UUID id, String newContents) {
        try {
            Optional<Comment> commentRead = commentRepository.findById(id);
            if (commentRead.isEmpty())
                return Result.error("Comment was not found", "404");
            Comment comment = commentRead.get();
            comment.setContents(newContents);
            commentRepository.saveAndFlush(comment);
            return Result.ok("Update ok");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Result.error("Failed to update comment", "500");
        }
    }

    public Result<String> delete(UUID id) {
        try {
            commentRepository.deleteById(id);
            return Result.ok("Delete ok");
        } catch (Exception e) {
            return Result.error("Failed to delete comment", "500");
        }
    }

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }
}
