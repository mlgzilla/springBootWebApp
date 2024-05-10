package task_tracker.dto;

import lombok.Data;
import task_tracker.domain.User;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class CommentDto {
    private UUID id;

    private String contents;

    private UserDto author;

    private LocalDateTime date;

    private UUID taskId;

    public CommentDto(UUID id, String contents, UserDto author, LocalDateTime date, UUID taskId) {
        this.id = id;
        this.contents = contents;
        this.author = author;
        this.date = date;
        this.taskId = taskId;
    }
}
