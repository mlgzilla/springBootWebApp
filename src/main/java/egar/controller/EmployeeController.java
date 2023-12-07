package egar.controller;

import egar.domain.employee.dto.EmployeeDtoRead;
import egar.domain.employee.entity.Employee;
import egar.domain.report.entity.Report;
import egar.domain.task.dto.TaskDtoRead;
import egar.domain.task.entity.Task;
import egar.enums.ContractType;
import egar.service.EmployeeService;
import egar.utils.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/employee")
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Integer id, Model model) {
        Result<EmployeeDtoRead> employeeRead = employeeService.findById(id);
        if (employeeRead.isError()) {
            model.addAttribute("message", employeeRead.getMessage());
            return employeeRead.getCode();
        } else
            model.addAttribute("employee", employeeRead.getObject());
        return "employee/show";
    }

    @GetMapping("/findByFirstName/{firstName}")
    public String findByFirstName(@PathVariable String firstName, Model model) {
        Result<List<EmployeeDtoRead>> employeeList = employeeService.findByFirstName(firstName);
        if (employeeList.isError()) {
            model.addAttribute("message", employeeList.getMessage());
            return employeeList.getCode();
        } else
            model.addAttribute("employeeList", employeeList);
        return "employee/showList";
    }

    @GetMapping("/findByMiddleName/{middleName}")
    public String findByMiddleName(@PathVariable String middleName, Model model) {
        Result<List<EmployeeDtoRead>> employeeList = employeeService.findByMiddleName(middleName);
        if (employeeList.isError()) {
            model.addAttribute("message", employeeList.getMessage());
            return employeeList.getCode();
        } else
            model.addAttribute("employeeList", employeeList);
        return "employee/showList";
    }

    @GetMapping("/findBySecondName/{secondName}")
    public String findBySecondName(@PathVariable String secondName, Model model) {
        Result<List<EmployeeDtoRead>> employeeList = employeeService.findBySecondName(secondName);
        if (employeeList.isError()) {
            model.addAttribute("message", employeeList.getMessage());
            return employeeList.getCode();
        } else
            model.addAttribute("employeeList", employeeList);
        return "employee/showList";
    }

    @GetMapping("/findByContractType/{contractType}")
    public String findByContractType(@PathVariable ContractType contractType, Model model) {
        Result<List<EmployeeDtoRead>> employeeList = employeeService.findByContractType(contractType);
        if (employeeList.isError()) {
            model.addAttribute("message", employeeList.getMessage());
            return employeeList.getCode();
        } else
            model.addAttribute("employeeList", employeeList);
        return "employee/showList";
    }

    @GetMapping("/new")
    public String getNew(Model model) {
        model.addAttribute(new Employee());
        return "employee/new";
    }

    @PostMapping("/")
    public String create(@ModelAttribute("employee") Employee employee, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("message", "Error in filled fields");
            return "400";
        }
        Result<EmployeeDtoRead> savedEmployee = employeeService.create(employee);
        if (savedEmployee.isError()) {
            model.addAttribute("message", savedEmployee.getMessage());
            return savedEmployee.getCode();
        } else {
            model.addAttribute("message", "Employee create ok");
            return "200";
        }
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Integer id, Model model) {
        Result<String> delete = employeeService.delete(id);
        if (delete.isError()) {
            model.addAttribute("message", delete.getMessage());
            return delete.getCode();
        } else {
            model.addAttribute("message", delete.getObject());
            return "200";
        }
    }
}
