package task_tracker.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import task_tracker.dto.TaskDto;
import task_tracker.enums.Priority;
import task_tracker.enums.TaskStatus;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "tasks")
@Data
@NoArgsConstructor
public class Task implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    private String name;

    private String description;

    @ManyToOne()
    @JoinColumn(name = "assignee", referencedColumnName = "id", foreignKey = @ForeignKey(name = "tasks_fk"))
    private User user;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @Column(name = "date_created")
    private LocalDateTime dateCreated;

    private LocalDateTime deadline;

    @ManyToOne
    @JoinColumn(name = "project", referencedColumnName = "id", foreignKey = @ForeignKey(name = "tasks_fk2"))
    private Project project;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    @OneToMany(mappedBy = "task")
    private Set<Report> reports;

    public TaskDto mapToDto() {
        return new TaskDto(
                this.id,
                this.name,
                this.description,
                this.user.getId(),
                this.status,
                this.dateCreated,
                this.deadline,
                this.project,
                this.priority
        );
    }
}
