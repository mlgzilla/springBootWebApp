package egar.domain.report.dto;

import java.time.LocalDateTime;

public class ReportCriteria {
    private Integer id;
    private String name;
    private String description;
    private LocalDateTime dateFiled;
    private Integer taskId; //TODO id or task???

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

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public ReportCriteria(Integer id, String name, String description, LocalDateTime dateFiled, Integer taskId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.dateFiled = dateFiled;
        this.taskId = taskId;
    }
}
