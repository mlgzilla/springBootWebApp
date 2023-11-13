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

    private Integer employeeId;

    private TaskStatus status;

    private Set<Report> reports;

    public TaskDtoRead(Integer id, String name, String description, LocalDateTime dueTime, Integer employeeId, TaskStatus status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.dueTime = dueTime;
        this.employeeId = employeeId;
        this.status = status;
    }
}
