package task_tracker.dto;

import lombok.Data;
import task_tracker.domain.Comment;
import task_tracker.domain.Project;
import task_tracker.enums.Priority;
import task_tracker.enums.TaskStatus;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
public class TaskDto {
    private UUID id;

    private String name;

    private String description;

    private UUID employeeId;

    private TaskStatus status;

    private LocalDateTime dateCreated;

    private LocalDateTime deadline;

    private UUID projectId;

    private Priority priority;

    private Set<Comment> comments;

    public TaskDto(UUID id, String name, String description, UUID employeeId, TaskStatus status, LocalDateTime dateCreated, LocalDateTime deadline, UUID project, Priority priority, Set<Comment> comments) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.employeeId = employeeId;
        this.status = status;
        this.dateCreated = dateCreated;
        this.deadline = deadline;
        this.projectId = project;
        this.priority = priority;
        this.comments = comments;
    }
}
