package task_tracker.service;

import task_tracker.enums.VacationStatus;
import task_tracker.repository.VacationRepository;
import task_tracker.utils.Result;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VacationService {
    private final VacationRepository vacationRepository;

    public VacationService(VacationRepository vacationRepository) {
        this.vacationRepository = vacationRepository;
    }

    public Result<VacationDto> findById(Integer id) {
        try {
            Optional<Vacation> vacation = vacationRepository.findById(id);
            if (vacation.isEmpty())
                return Result.error("Vacation was not found", "404");
            else
                return Result.ok(vacation.get().mapToDto());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Result.error("Error finding vacation", "500");
        }
    }

    public Result<List<VacationDto>> findByEmployeeId(Integer id) {
        try {
            List<Vacation> vacations = vacationRepository.findByEmployeeId(id);
            if (vacations.isEmpty())
                return Result.error("Vacations by employee were not found", "404");
            else
                return Result.ok(vacations.stream().map(Vacation::mapToDto).collect(Collectors.toList()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Result.error("Error finding vacation by employee", "500");
        }
    }

    public Result<List<VacationDto>> findByStatus(VacationStatus status) {
        try {
            List<Vacation> vacations = vacationRepository.findByStatus(status);
            if (vacations.isEmpty())
                return Result.error("Vacations by status were not found", "404");
            else
                return Result.ok(vacations.stream().map(Vacation::mapToDto).collect(Collectors.toList()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Result.error("Error finding vacation by status", "500");
        }
    }

    public Result<List<VacationDto>> findByEmployeeIdAndStatusInRange(Integer id, VacationStatus status, LocalDateTime timeStart, LocalDateTime timeFinish) {
        try {
            List<Vacation> vacations = vacationRepository.findByEmployeeIdAndStatusInRange(id, status, timeStart, timeFinish);
            if (vacations.isEmpty())
                return Result.error("Vacations by employee and status in range were not found", "404");
            else
                return Result.ok(vacations.stream().map(Vacation::mapToDto).collect(Collectors.toList()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Result.error("Error finding vacation by employee and status in range", "500");
        }
    }

    public Result<VacationDto> create(Vacation vacation) {
        try {
            Vacation savedVacation = vacationRepository.saveAndFlush(vacation);
            return Result.ok(savedVacation.mapToDto());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Result.error("Error creating vacation", "500");
        }
    }

    public Result<String> update(VacationDto vacationDto) {
        try {
            Optional<Vacation> vacationRead = vacationRepository.findById(vacationDto.getId());
            if (vacationRead.isEmpty())
                return Result.error("Vacation was not found", "404");
            Vacation vacation = vacationRead.get();
            vacation.setTimeStart(vacationDto.getTimeStart());
            vacation.setTimeFinish(vacationDto.getTimeFinish());
            vacation.setDescription(vacationDto.getDescription());
            vacationRepository.saveAndFlush(vacation);
            return Result.ok("Update ok");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Result.error("Failed to update vacation", "500");
        }
    }

    public Result<String> updateStatus(Integer id, VacationStatus vacationStatus) {
        try {
            Optional<Vacation> vacationRead = vacationRepository.findById(id);
            if (vacationRead.isEmpty())
                return Result.error("Vacation was not found", "404");
            Vacation vacation = vacationRead.get();
            vacation.setStatus(vacationStatus);
            vacationRepository.saveAndFlush(vacation);
            return Result.ok("Update ok");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Result.error("Failed to update vacation", "500");
        }
    }

    public Result<String> delete(Integer id) {
        try {
            vacationRepository.deleteById(id);
            return Result.ok("Delete ok");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Result.error("Failed to delete vacation", "500");
        }
    }
}
