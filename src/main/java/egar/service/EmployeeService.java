package egar.service;

import egar.domain.employee.dto.EmployeeReadDto;
import egar.domain.report.dto.ReportDtoRead;
import egar.enums.ContractType;
import egar.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public List<EmployeeReadDto> findByContractType(ContractType contractType){
        return employeeRepository.findByContractType(contractType).stream().map(
                employee -> new EmployeeReadDto(
                        employee.getId(),
                        employee.getFirstName(),
                        employee.getMiddleName(),
                        employee.getSecondName(),
                        employee.getPhoneNumber(),
                        employee.getCardNumber(),
                        employee.getContractType()
                )).collect(Collectors.toList());
    }
}
