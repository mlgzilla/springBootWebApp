package task_tracker.service;

import org.springframework.stereotype.Service;
import task_tracker.domain.WorkTime;
import task_tracker.dto.WorkTimeDto;
import task_tracker.repository.WorkTimeRepository;
import task_tracker.utils.Result;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class WorkTimeService {
    private final WorkTimeRepository workTimeRepository;

    public WorkTimeService(WorkTimeRepository workTimeRepository) {
        this.workTimeRepository = workTimeRepository;
    }

    public Result<WorkTimeDto> findById(UUID id) {
        try {
            Optional<WorkTime> workTime = workTimeRepository.findById(id);
            if (workTime.isEmpty())
                return Result.error("WorkTime was not found", "404");
            else
                return Result.ok(workTime.get().mapToDto());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Result.error("Error finding work time", "500");
        }
    }

    public Result<List<WorkTimeDto>> findByUserId(UUID id) {
        try {
            List<WorkTime> workTime = workTimeRepository.findByUserId(id);
            if (workTime.isEmpty())
                return Result.error("WorkTime by User id were not found", "404");
            else
                return Result.ok(workTime.stream().map(WorkTime::mapToDto).collect(Collectors.toList()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Result.error("Error finding WorkTime by User id", "500");
        }
    }

    public Result<List<WorkTimeDto>> findByUserIdInRange(UUID id, LocalDateTime timeStart, LocalDateTime timeFinish) {
        try {
            List<WorkTime> workTime = workTimeRepository.findByUserIdInRange(id, timeStart, timeFinish);
            if (workTime.isEmpty())
                return Result.error("WorkTime by User id in range were not found", "404");
            else
                return Result.ok(workTime.stream().map(WorkTime::mapToDto).collect(Collectors.toList()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Result.error("Error finding WorkTime by User id in range", "500");
        }
    }

    public Result<WorkTimeDto> create(WorkTime workTime) {
        try {
            WorkTime savedWorkTime = workTimeRepository.saveAndFlush(workTime);
            return Result.ok(savedWorkTime.mapToDto());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Result.error("Failed to create work time", "500");
        }
    }

    public Result<String> update(UUID id, WorkTimeDto workTimeDto) {
        try {
            Optional<WorkTime> workTimeRead = workTimeRepository.findById(id);
            if (workTimeRead.isEmpty())
                return Result.error("WorkTime was not found", "404");
            WorkTime workTime = workTimeRead.get();
            workTime.setTimeStart(workTimeDto.getTimeStart());
            workTime.setTimeFinish(workTimeDto.getTimeFinish());
            workTime.setComment(workTimeDto.getComment());
            workTimeRepository.saveAndFlush(workTime);
            return Result.ok("Update ok");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Result.error("Failed to update workTime", "500");
        }
    }

    public Result<String> delete(UUID id) {
        try {
            workTimeRepository.deleteById(id);
            return Result.ok("Delete ok");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Result.error("Failed to delete work time", "500");
        }
    }
}
