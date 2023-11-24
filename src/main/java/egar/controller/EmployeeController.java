package egar.controller;

import egar.domain.employee.dto.EmployeeDtoRead;
import egar.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/employee")
public class EmployeeController {
    private final EmployeeService employeeService;
    
    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Integer id, Model model){
        Optional<EmployeeDtoRead> employeeRead = employeeService.findById(id);
        if(employeeRead.isEmpty())
            return "404";
        else
            model.addAttribute("employee", employeeRead.get());
        return "employee/show";
    }

    @GetMapping("/findByFirstName/{firstName}")
    public String findByFirstName(@PathVariable String firstName, Model model) {
        List<EmployeeDtoRead> employeeList = employeeService.findByFirstName(firstName);
        model.addAttribute("employeeList", employeeList);
        return "employee/showList";
    }
    @GetMapping("/findByMiddleName/{middleName}")
    public String findByMiddleName(@PathVariable String middleName, Model model) {
        List<EmployeeDtoRead> employeeList = employeeService.findByMiddleName(middleName);
        model.addAttribute("employeeList", employeeList);
        return "employee/showList";
    }
    @GetMapping("/findBySecondName/{secondName}")
    public String findBySecondName(@PathVariable String secondName, Model model) {
        List<EmployeeDtoRead> employeeList = employeeService.findBySecondName(secondName);
        model.addAttribute("employeeList", employeeList);
        return "employee/showList";
    }


}
