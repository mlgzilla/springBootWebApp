package egar.domain.report.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ReportDtoRead {
    private Integer id;
    private String name;
    private String description;
    private LocalDateTime dateFiled;
    private Integer taskId; //TODO id or task???

    public ReportDtoRead(Integer id, String name, String description, LocalDateTime dateFiled, Integer taskId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.dateFiled = dateFiled;
        this.taskId = taskId;
    }
}
