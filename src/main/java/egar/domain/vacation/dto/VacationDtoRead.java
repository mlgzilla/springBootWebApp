package egar.domain.vacation.dto;

import egar.enums.VacationStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class VacationDtoRead {
    private Integer id;

    private LocalDateTime timeStart;

    private LocalDateTime timeFinish;

    private Integer employeeId;

    private String description;

    private VacationStatus status;

    public VacationDtoRead(Integer id, LocalDateTime timeStart, LocalDateTime timeFinish, Integer employeeId, String description, VacationStatus status) {
        this.id = id;
        this.timeStart = timeStart;
        this.timeFinish = timeFinish;
        this.employeeId = employeeId;
        this.description = description;
        this.status = status;
    }
}
