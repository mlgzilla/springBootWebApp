package task_tracker.service;

import task_tracker.dto.WorkTimeDto;
import task_tracker.domain.WorkTime;
import task_tracker.enums.VacationStatus;
import task_tracker.repository.WorkTimeRepository;
import task_tracker.utils.Result;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WorkHoursService {
    private final WorkTimeRepository workTimeRepository;
    private final VacationRepository vacationRepository;

    public WorkHoursService(WorkTimeRepository workTimeRepository, VacationRepository vacationRepository) {
        this.workTimeRepository = workTimeRepository;
        this.vacationRepository = vacationRepository;
    }

    public Result<WorkTimeDto> findById(Integer id) {
        try {
            Optional<WorkTime> workHours = workTimeRepository.findById(id);
            if (workHours.isEmpty())
                return Result.error("WorkHours was not found", "404");
            else
                return Result.ok(workHours.get().mapToDto());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Result.error("Error finding work hours", "500");
        }
    }

    public Result<List<WorkTimeDto>> findByEmployeeId(Integer id) {
        try {
            List<WorkTime> workHours = workTimeRepository.findByEmployeeId(id);
            if (workHours.isEmpty())
                return Result.error("WorkHours by Employee id were not found", "404");
            else
                return Result.ok(workHours.stream().map(WorkTime::mapToDto).collect(Collectors.toList()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Result.error("Error finding WorkHours by Employee id", "500");
        }
    }

    public Result<List<WorkTimeDto>> findByEmployeeIdInRange(Integer id, LocalDateTime timeStart, LocalDateTime timeFinish) {
        try {
            List<WorkTime> workHours = workTimeRepository.findByEmployeeIdInRange(id, timeStart, timeFinish);
            if (workHours.isEmpty())
                return Result.error("WorkHours by Employee id in range were not found", "404");
            else
                return Result.ok(workHours.stream().map(WorkTime::mapToDto).collect(Collectors.toList()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Result.error("Error finding WorkHours by Employee id in range", "500");
        }
    }

    public Result<WorkTimeDto> create(WorkTime workTime) {
        try {
            if (vacationRepository.findByEmployeeIdAndStatusInRange(workTime.getUser().getId(), VacationStatus.OnGoing, workTime.getTimeStart(), workTime.getTimeFinish()).isEmpty())
                return Result.error("Cant create work hours due to ongoing vacation", "500"); //TODO 403?
            WorkTime savedWorkTime = workTimeRepository.saveAndFlush(workTime);
            return Result.ok(savedWorkTime.mapToDto());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Result.error("Failed to create work hours", "500");
        }
    }

    public Result<String> update(Integer id, WorkTimeDto workHoursDto) {
        try {
            Optional<WorkTime> workHoursRead = workTimeRepository.findById(id);
            if (workHoursRead.isEmpty())
                return Result.error("WorkHours was not found", "404");
            WorkTime workTime = workHoursRead.get();
            workTime.setTimeStart(workHoursDto.getTimeStart());
            workTime.setTimeFinish(workHoursDto.getTimeFinish());
            workTime.setComment(workHoursDto.getComment());
            workTimeRepository.saveAndFlush(workTime);
            return Result.ok("Update ok");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Result.error("Failed to update workHours", "500");
        }
    }

    public Result<String> delete(Integer id) {
        try {
            workTimeRepository.deleteById(id);
            return Result.ok("Delete ok");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Result.error("Failed to delete work hours", "500");
        }
    }
}
