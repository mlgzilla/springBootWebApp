package task_tracker.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class ReportDto {
    private UUID id;
    private String name;
    private String description;
    private LocalDateTime dateFiled;
    private UUID taskId;

    public ReportDto(UUID id, String name, String description, LocalDateTime dateFiled, UUID taskId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.dateFiled = dateFiled;
        this.taskId = taskId;
    }
}
