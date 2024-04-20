package task_tracker.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class WorkTimeDto {
    private UUID id;

    private LocalDateTime timeStart;

    private LocalDateTime timeFinish;

    private UUID employeeId;

    private String comment;

    public WorkTimeDto(UUID id, LocalDateTime timeStart, LocalDateTime timeFinish, UUID employeeId, String comment) {
        this.id = id;
        this.timeStart = timeStart;
        this.timeFinish = timeFinish;
        this.employeeId = employeeId;
        this.comment = comment;
    }
}
