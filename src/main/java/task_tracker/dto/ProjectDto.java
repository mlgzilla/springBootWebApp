package task_tracker.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class ProjectDto {
    private UUID id;

    private String name;

    public ProjectDto(UUID id, String name) {
        this.id = id;
        this.name = name;
    }
}
