package egar.service;

import egar.domain.vacation.dto.VacationDtoRead;
import egar.domain.vacation.entity.Vacation;
import egar.enums.VacationStatus;
import egar.repository.VacationRepository;
import egar.utils.Result;
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

    public Result<VacationDtoRead> findById(Integer id){
        Vacation vacation = vacationRepository.findById(id);
        if (vacation == null)
            return Result.error("Vacation was not found");
        else
            return Result.ok(vacation.mapToDto());
    }

    public Optional<VacationDtoRead> create(Vacation vacation){
        try{
            Vacation savedVacation = vacationRepository.saveAndFlush(vacation);
            return Optional.of(savedVacation.mapToDto());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }

    public List<VacationDtoRead> findByEmployeeId(Integer id){
        return vacationRepository.findByEmployeeId(id).stream().map(Vacation::mapToDto).collect(Collectors.toList());
    }
    public List<VacationDtoRead> findByEmployeeIdAndStatus(Integer id, VacationStatus status){
        return vacationRepository.findByEmployeeIdAndStatus(id, status).stream().map(Vacation::mapToDto).collect(Collectors.toList());
    }
    public List<VacationDtoRead> findByEmployeeIdAndStatusInRange(Integer id, VacationStatus status, LocalDateTime timeStart, LocalDateTime timeFinish){
        return vacationRepository.findByEmployeeIdAndStatusInRange(id, status, timeStart, timeFinish).stream().map(Vacation::mapToDto).collect(Collectors.toList());
    }

}
