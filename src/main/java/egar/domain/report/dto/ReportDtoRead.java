package egar.domain.report.dto;

import egar.domain.task.entity.Task;

import java.time.LocalDateTime;

public class ReportDtoRead {
    private Integer id;
    private String name;
    private String description;
    private LocalDateTime dateFiled;
    private Task task; //TODO id or task???

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

    public ReportDtoRead(Integer id, String name, String description, LocalDateTime dateFiled, Task task) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.dateFiled = dateFiled;
        this.task = task;
    }
}
