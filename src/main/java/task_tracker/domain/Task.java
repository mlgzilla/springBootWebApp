package task_tracker.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;
import task_tracker.dto.TaskDto;
import task_tracker.enums.Priority;
import task_tracker.enums.TaskStatus;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tasks")
@Data
@NoArgsConstructor
public class Task implements Serializable {
    @Id
    @GeneratedValue(generator = "uuid-hibernate-generator")
    @GenericGenerator(name = "uuid-hibernate-generator", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    private String name;

    private String description;

    @ManyToOne()
    @JoinColumn(name = "assignee", referencedColumnName = "id", foreignKey = @ForeignKey(name = "tasks_fk"))
    private User user;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @Column(name = "date_created")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime dateCreated;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime deadline;

    @Column(name = "project_id")
    private UUID projectId;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    public TaskDto mapToDto() {
        return new TaskDto(
                this.id,
                this.name,
                this.description,
                this.user.mapToDto(),
                this.status,
                this.dateCreated,
                this.deadline,
                this.projectId,
                this.priority
        );
    }
}
