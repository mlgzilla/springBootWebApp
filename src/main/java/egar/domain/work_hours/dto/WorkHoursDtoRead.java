package egar.domain.work_hours.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WorkHoursDtoRead {
    private Integer id;

    private LocalDateTime timeStart;

    private LocalDateTime timeFinish;

    private Integer employeeId;

    private String comment;

    public WorkHoursDtoRead(Integer id, LocalDateTime timeStart, LocalDateTime timeFinish, Integer employeeId, String comment) {
        this.id = id;
        this.timeStart = timeStart;
        this.timeFinish = timeFinish;
        this.employeeId = employeeId;
        this.comment = comment;
    }
}
