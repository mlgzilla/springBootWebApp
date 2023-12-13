package egar.service;

import egar.domain.work_hours.dto.WorkHoursDtoRead;
import egar.domain.work_hours.entity.WorkHours;
import egar.enums.VacationStatus;
import egar.repository.VacationRepository;
import egar.repository.WorkHoursRepository;
import egar.utils.Result;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WorkHoursService {
    private final WorkHoursRepository workHoursRepository;
    private final VacationRepository vacationRepository;

    public WorkHoursService(WorkHoursRepository workHoursRepository, VacationRepository vacationRepository) {
        this.workHoursRepository = workHoursRepository;
        this.vacationRepository = vacationRepository;
    }

    public Result<WorkHoursDtoRead> findById(Integer id) {
        try {
            Optional<WorkHours> workHours = workHoursRepository.findById(id);
            if (workHours.isEmpty())
                return Result.error("WorkHours was not found", "404");
            else
                return Result.ok(workHours.get().mapToDto());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Result.error("Error finding work hours", "500");
        }
    }

    public Result<List<WorkHoursDtoRead>> findByEmployeeId(Integer id) {
        try {
            List<WorkHours> workHours = workHoursRepository.findByEmployeeId(id);
            if (workHours.isEmpty())
                return Result.error("WorkHours by Employee id were not found", "404");
            else
                return Result.ok(workHours.stream().map(WorkHours::mapToDto).collect(Collectors.toList()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Result.error("Error finding WorkHours by Employee id", "500");
        }
    }

    public Result<List<WorkHoursDtoRead>> findByEmployeeIdInRange(Integer id, LocalDateTime timeStart, LocalDateTime timeFinish) {
        try {
            List<WorkHours> workHours = workHoursRepository.findByEmployeeIdInRange(id, timeStart, timeFinish);
            if (workHours.isEmpty())
                return Result.error("WorkHours by Employee id in range were not found", "404");
            else
                return Result.ok(workHours.stream().map(WorkHours::mapToDto).collect(Collectors.toList()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Result.error("Error finding WorkHours by Employee id in range", "500");
        }
    }

    public Result<WorkHoursDtoRead> create(WorkHours workHours) {
        try {
            if (vacationRepository.findByEmployeeIdAndStatusInRange(workHours.getEmployee().getId(), VacationStatus.OnGoing, workHours.getTimeStart(), workHours.getTimeFinish()).isEmpty())
                return Result.error("Cant create work hours due to ongoing vacation", "500"); //TODO 403?
            WorkHours savedWorkHours = workHoursRepository.saveAndFlush(workHours);
            return Result.ok(savedWorkHours.mapToDto());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Result.error("Failed to create work hours", "500"); //TODO 403?
        }
    }

    public Result<String> update(Integer id, WorkHoursDtoRead workHoursDto) {
        try {
            Optional<WorkHours> workHoursRead = workHoursRepository.findById(id);
            if (workHoursRead.isEmpty())
                return Result.error("WorkHours was not found", "404");
            WorkHours workHours = workHoursRead.get();
            workHours.setTimeStart(workHoursDto.getTimeStart());
            workHours.setTimeFinish(workHoursDto.getTimeFinish());
            workHours.setComment(workHoursDto.getComment());
            workHoursRepository.saveAndFlush(workHours);
            return Result.ok("Update ok");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Result.error("Failed to update workHours", "500");
        }
    }

    public Result<String> delete(Integer id) {
        try {
            workHoursRepository.deleteById(id);
            return Result.ok("Delete ok");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Result.error("Failed to delete work hours", "500");
        }
    }
}
