package egar.domain.task.entity;

import egar.domain.employee.entity.Employee;
import egar.domain.report.entity.Report;
import egar.enums.TaskStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "task")
@Data
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;
    @Column(name = "due_time")
    private LocalDateTime dueTime;
    @ManyToOne
    @JoinColumn(name = "assignee")
    private Employee employee;

    private TaskStatus status;

    @OneToMany(mappedBy = "task")
    private Set<Report> reports;

}
