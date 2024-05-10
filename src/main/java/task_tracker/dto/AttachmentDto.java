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

    private UserDto user;

    private LocalDateTime dateUploaded;

    private TaskDto task;

    public AttachmentDto(UUID id, String name, String path, Integer size, UserDto user, LocalDateTime dateUploaded, TaskDto task) {
        this.id = id;
        this.name = name;
        this.path = path;
        this.size = size;
        this.user = user;
        this.dateUploaded = dateUploaded;
        this.task = task;
    }
}
