package egar.service;

import egar.domain.employee.dto.EmployeeDtoRead;
import egar.domain.employee.dto.EmployeeDtoRead;
import egar.domain.employee.entity.Employee;
import egar.enums.ContractType;
import egar.repository.EmployeeRepository;
import egar.utils.Result;
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

    public Result<EmployeeDtoRead> findById(Integer id) {
        Employee employee = employeeRepository.findById(id);
        if (employee == null)
            return Result.error("Employee was not found");
        else
            return Result.ok(employee.mapToDto());
    }

    public List<EmployeeDtoRead> findByFirstName(String firstName) {
        return employeeRepository.findByFirstName(firstName).stream().map(Employee::mapToDto).collect(Collectors.toList());
    }

    public List<EmployeeDtoRead> findByMiddleName(String middleName) {
        return employeeRepository.findByMiddleName(middleName).stream().map(Employee::mapToDto).collect(Collectors.toList());
    }
    public List<EmployeeDtoRead> findBySecondName(String secondName) {
        return employeeRepository.findBySecondName(secondName).stream().map(Employee::mapToDto).collect(Collectors.toList());
    }

    public List<EmployeeDtoRead> findByContractType(ContractType contractType) {
        return employeeRepository.findByContractType(contractType).stream().map(Employee::mapToDto).collect(Collectors.toList());
    }

}
