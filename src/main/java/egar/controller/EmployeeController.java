package egar.controller;

import egar.domain.employee.dto.EmployeeDtoRead;
import egar.domain.employee.entity.Employee;
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
            model.addAttribute("employeeList", employeeList.getObject());
        return "employee/showList";
    }

    @GetMapping("/findByMiddleName/{middleName}")
    public String findByMiddleName(@PathVariable String middleName, Model model) {
        Result<List<EmployeeDtoRead>> employeeList = employeeService.findByMiddleName(middleName);
        if (employeeList.isError()) {
            model.addAttribute("message", employeeList.getMessage());
            return employeeList.getCode();
        } else
            model.addAttribute("employeeList", employeeList.getObject());
        return "employee/showList";
    }

    @GetMapping("/findBySecondName/{secondName}")
    public String findBySecondName(@PathVariable String secondName, Model model) {
        Result<List<EmployeeDtoRead>> employeeList = employeeService.findBySecondName(secondName);
        if (employeeList.isError()) {
            model.addAttribute("message", employeeList.getMessage());
            return employeeList.getCode();
        } else
            model.addAttribute("employeeList", employeeList.getObject());
        return "employee/showList";
    }

    @GetMapping("/findByContractType/{contractType}")
    public String findByContractType(@PathVariable ContractType contractType, Model model) {
        Result<List<EmployeeDtoRead>> employeeList = employeeService.findByContractType(contractType);
        if (employeeList.isError()) {
            model.addAttribute("message", employeeList.getMessage());
            return employeeList.getCode();
        } else
            model.addAttribute("employeeList", employeeList.getObject());
        return "employee/showList";
    }

    @GetMapping("/new")
    public String getNew(Model model) {
        model.addAttribute(new Employee());
        model.addAttribute("types", ContractType.values());
        return "employee/new";
    }

    @GetMapping("/update/{id}")
    public String getUpdateForm(@PathVariable("id") Integer id, Model model) {
        Result<EmployeeDtoRead> employeeRead = employeeService.findById(id);
        if (employeeRead.isError()) {
            model.addAttribute("message", employeeRead.getMessage());
            return employeeRead.getCode();
        }
        model.addAttribute("employee", employeeRead.getObject());
        return "employee/update";
    }

    @GetMapping("/")
    public String getHome(Model model) {
        model.addAttribute("types", ContractType.values());
        return "employee/index";
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
            return "redirect:/employee/" + savedEmployee.getObject().getId();
        }
    }

    @PostMapping("/submit")
    public String inputSubmit(
            @RequestParam(required = false, name = "id") Integer id,
            @RequestParam(required = false, name = "firstName") String firstName,
            @RequestParam(required = false, name = "middleName") String middleName,
            @RequestParam(required = false, name = "secondName") String secondName,
            @RequestParam(required = false, name = "contractType") ContractType contractType
    ) {
        if (id != null)
            return "redirect:/employee/" + id;
        if (firstName != null)
            return "redirect:/employee/findByFirstName/" + firstName;
        if (middleName != null)
            return "redirect:/employee/findByMiddleName/" + middleName;
        if (secondName != null)
            return "redirect:/employee/findBySecondName/" + secondName;
        if (contractType != null)
            return "redirect:/employee/findByContractType/" + contractType;

        return "redirect:/employee/";
    }

    @PutMapping("/{id}")
    public String update(@ModelAttribute("employee") EmployeeDtoRead employee, @PathVariable("id") Integer id, Model model) {
        Result<String> upload = employeeService.update(id, employee);
        if (upload.isError()) {
            model.addAttribute("message", upload.getMessage());
            return upload.getCode();
        } else
            return "redirect:/employee/" + id;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Integer id, Model model) {
        Result<String> delete = employeeService.delete(id);
        if (delete.isError()) {
            model.addAttribute("message", delete.getMessage());
            return delete.getCode();
        } else {
            model.addAttribute("message", delete.getObject());
            return "redirect:/employee/";
        }
    }
}
