package task_tracker.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class CommentDto {
    private UUID id;

    private String contents;

    private UUID authorId;

    private LocalDateTime date;

    private UUID taskId;

    public CommentDto(UUID id, String contents, UUID authorId, LocalDateTime date, UUID taskId) {
        this.id = id;
        this.contents = contents;
        this.authorId = authorId;
        this.date = date;
        this.taskId = taskId;
    }
}
