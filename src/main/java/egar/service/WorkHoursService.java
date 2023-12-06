package egar.service;

import egar.domain.vacation.entity.Vacation;
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

    public Result<WorkHoursDtoRead> findById(Integer id){
        WorkHours workHours = workHoursRepository.findById(id);
        if (workHours == null)
            return Result.error("WorkHours was not found");
        else
            return Result.ok(workHours.mapToDto());
    }

    public Optional<WorkHoursDtoRead> create(WorkHours workHours){
        try{
            if (vacationRepository.findByEmployeeIdAndStatusInRange(workHours.getEmployee().getId(), VacationStatus.OnGoing, workHours.getTimeStart(), workHours.getTimeFinish()).isEmpty())
                return Optional.empty();
            WorkHours savedWorkHours = workHoursRepository.saveAndFlush(workHours);
            return Optional.of(savedWorkHours.mapToDto());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }

    public List<WorkHoursDtoRead> findByEmployeeId(Integer id){
        return workHoursRepository.findByEmployeeId(id).stream().map(WorkHours::mapToDto).collect(Collectors.toList());
    }

    public List<WorkHoursDtoRead> findByEmployeeIdInRange(Integer id, LocalDateTime timeStart, LocalDateTime timeFinish){
        return workHoursRepository.findByEmployeeIdInRange(id, timeStart, timeFinish).stream().map(WorkHours::mapToDto).collect(Collectors.toList());
    }


}
