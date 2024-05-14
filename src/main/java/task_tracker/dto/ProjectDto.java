package task_tracker.dto;

import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
public class ProjectDto {
    private UUID id;

    private String name;

    private Set<UUID> users;

    private Set<UUID> tasks;

    public ProjectDto(UUID id, String name, Set<UUID> users, Set<UUID> tasks) {
        this.id = id;
        this.name = name;
        this.users = users;
        this.tasks = tasks;
    }
}
