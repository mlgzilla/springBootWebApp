package egar.domain.report.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReportDtoRead {
    private Integer id;
    private String name;
    private String description;
    private LocalDateTime dateFiled;
    private Integer taskId;

    public ReportDtoRead(Integer id, String name, String description, LocalDateTime dateFiled, Integer taskId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.dateFiled = dateFiled;
        this.taskId = taskId;
    }
}
