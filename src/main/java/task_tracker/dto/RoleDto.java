package task_tracker.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class RoleDto {
    private UUID id;

    private String name;

    public RoleDto(UUID id, String name) {
        this.id = id;
        this.name = name;
    }
}
