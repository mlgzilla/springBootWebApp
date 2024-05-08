package task_tracker.dto;

import lombok.Data;
import task_tracker.domain.User;

import java.util.Set;
import java.util.UUID;

@Data
public class ProjectDto {
    private UUID id;

    private String name;

    private Set<UUID> users;

    public ProjectDto(UUID id, String name, Set<UUID> users) {
        this.id = id;
        this.name = name;
        this.users = users;
    }
}
