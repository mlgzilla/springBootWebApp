package egar.domain.task.dto;

import egar.domain.employee.entity.Employee;
import egar.domain.report.entity.Report;
import egar.enums.TaskStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class TaskDtoRead {
    private Integer id;
    private String name;
    private String description;
    private LocalDateTime dueTime;
    private Employee employee;
    private TaskStatus status;
    private Set<Report> reports;
}
