package egar.domain.task.entity;

import egar.domain.employee.entity.Employee;
import egar.domain.report.entity.Report;
import egar.enums.TaskStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "task")
@Data
@NoArgsConstructor
public class Task implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;
    @Column(name = "due_time")
    private LocalDateTime dueTime;
    @ManyToOne()
    @JoinColumn(name = "assignee", referencedColumnName = "id", foreignKey = @ForeignKey(name = "task_fk"))
    private Employee employee;
    @Enumerated(EnumType.STRING)
    private TaskStatus status;
    @OneToMany(mappedBy = "task")
    private Set<Report> reports;
}
