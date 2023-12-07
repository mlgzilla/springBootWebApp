package egar.service;

import egar.domain.document.dto.DocumentDtoRead;
import egar.domain.document.entity.Document;
import egar.domain.employee.dto.EmployeeDtoRead;
import egar.domain.employee.dto.EmployeeDtoRead;
import egar.domain.employee.entity.Employee;
import egar.domain.report.dto.ReportDtoRead;
import egar.domain.report.entity.Report;
import egar.enums.ContractType;
import egar.repository.*;
import egar.utils.Result;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final DocumentRepository documentRepository;
    private final TaskRepository taskRepository;
    private final VacationRepository vacationRepository;
    private final WorkHoursRepository workHoursRepository;

    public EmployeeService(EmployeeRepository employeeRepository, DocumentRepository documentRepository, TaskRepository taskRepository, VacationRepository vacationRepository, WorkHoursRepository workHoursRepository) {
        this.employeeRepository = employeeRepository;
        this.documentRepository = documentRepository;
        this.taskRepository = taskRepository;
        this.vacationRepository = vacationRepository;
        this.workHoursRepository = workHoursRepository;
    }

    public Result<EmployeeDtoRead> findById(Integer id) {
        try {
            Employee employee = employeeRepository.findById(id);
            if (employee == null)
                return Result.error("Employee was not found", "404");
            else
                return Result.ok(employee.mapToDto());
        } catch (Exception e) {
            return Result.error("Error finding employee", "500");
        }

    }

    public Result<List<EmployeeDtoRead>> findByFirstName(String firstName) {
        try {
            List<Employee> employees = employeeRepository.findByFirstName(firstName);
            if (employees.isEmpty())
                return Result.error("Employees by first name were not found", "404");
            else
                return Result.ok(employees.stream().map(Employee::mapToDto).collect(Collectors.toList()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Result.error("Error finding Employees by first name", "500");
        }
    }

    public Result<List<EmployeeDtoRead>> findByMiddleName(String middleName) {
        try {
            List<Employee> employees = employeeRepository.findByMiddleName(middleName);
            if (employees.isEmpty())
                return Result.error("Employees by middle name were not found", "404");
            else
                return Result.ok(employees.stream().map(Employee::mapToDto).collect(Collectors.toList()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Result.error("Error finding Employees by middle name", "500");
        }
    }
    public Result<List<EmployeeDtoRead>> findBySecondName(String secondName) {
        try {
            List<Employee> employees = employeeRepository.findBySecondName(secondName);
            if (employees.isEmpty())
                return Result.error("Employees by second name were not found", "404");
            else
                return Result.ok(employees.stream().map(Employee::mapToDto).collect(Collectors.toList()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Result.error("Error finding Employees by second name", "500");
        }
    }

    public Result<List<EmployeeDtoRead>> findByContractType(ContractType contractType) {
        try {
            List<Employee> employees = employeeRepository.findByContractType(contractType);
            if (employees.isEmpty())
                return Result.error("Employees by contract type were not found", "404");
            else
                return Result.ok(employees.stream().map(Employee::mapToDto).collect(Collectors.toList()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Result.error("Error finding Employees by contract type", "500");
        }
    }

    public Result<EmployeeDtoRead> create(Employee employee){
        try{
            Employee savedEmployee = employeeRepository.saveAndFlush(employee);
            return Result.ok(savedEmployee.mapToDto());
        } catch (Exception e) {
            return Result.error("Error creating Employee", "500");
        }
    }

    public Result<String> delete(Integer id) {
        try {
            documentRepository.deleteAllByEmployeeId(id);
            taskRepository.deleteAllByEmployeeId(id);
            vacationRepository.deleteAllByEmployeeId(id);
            workHoursRepository.deleteAllByEmployeeId(id);
            employeeRepository.deleteById(id);
            return Result.ok("Delete ok");
        } catch (Exception e) {
            return Result.error("Failed to delete employee", "500");
        }
    }
}
