package egar.service;

import egar.domain.employee.dto.EmployeeReadDto;
import egar.domain.report.dto.ReportDtoRead;
import egar.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Optional<EmployeeReadDto> findById(Integer id){
        return employeeRepository.findById(id).map
                (employee -> new EmployeeReadDto(
                        employee.getId(),
                        employee.getFirstName(),
                        employee.getMiddleName(),
                        employee.getSecondName(),
                        employee.getPhoneNumber(),
                        employee.getCardNumber(),
                        employee.getContractType()
                ));
    }
}
