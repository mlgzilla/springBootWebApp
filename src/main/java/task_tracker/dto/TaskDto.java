package task_tracker.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import task_tracker.enums.Priority;
import task_tracker.enums.TaskStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
public class TaskDto {
    private UUID id;

    private String name;

    private String description;

    private UserDto user;

    private TaskStatus status;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime dateCreated;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime deadline;

    private UUID projectId;

    private Priority priority;

    public TaskDto(UUID id, String name, String description, UserDto user, TaskStatus status, LocalDateTime dateCreated, LocalDateTime deadline, UUID project, Priority priority) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.user = user;
        this.status = status;
        this.dateCreated = dateCreated;
        this.deadline = deadline;
        this.projectId = project;
        this.priority = priority;
    }
}
