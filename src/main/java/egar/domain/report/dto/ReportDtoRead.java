package egar.domain.report.dto;

import egar.domain.task.entity.Task;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReportDtoRead {
    private Integer id;
    private String name;
    private String description;
    private LocalDateTime dateFiled;
    private Task task; //TODO id or task???
}
