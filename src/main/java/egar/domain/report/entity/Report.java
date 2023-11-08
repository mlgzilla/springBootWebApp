package egar.domain.report.entity;

import egar.domain.task.entity.Task;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "report")
@Data
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;
    @Column(name = "date_filed")
    private LocalDateTime dateFiled;
    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    public Report() {
    }
}
