package egar.domain.report.entity;

import egar.domain.task.entity.Task;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "report")
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

    public Report(Integer id, String name, String description, LocalDateTime dateFiled, Task task) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.dateFiled = dateFiled;
        this.task = task;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDateFiled() {
        return dateFiled;
    }

    public void setDateFiled(LocalDateTime dateFiled) {
        this.dateFiled = dateFiled;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }
}
