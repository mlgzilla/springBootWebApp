package task_tracker.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class AttachmentDto {
    private UUID id;

    private String name;

    private String path;

    private Integer size;

    private UUID userId;

    private LocalDateTime dateUploaded;

    private UUID taskId;

    public AttachmentDto(UUID id, String name, String path, Integer size, UUID userId, LocalDateTime dateUploaded, UUID taskId) {
        this.id = id;
        this.name = name;
        this.path = path;
        this.size = size;
        this.userId = userId;
        this.dateUploaded = dateUploaded;
        this.taskId = taskId;
    }
}
