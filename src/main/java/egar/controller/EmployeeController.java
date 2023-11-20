package egar.controller;

import egar.domain.employee.dto.EmployeeReadDto;
import egar.domain.report.dto.ReportDtoRead;
import egar.enums.ContractType;
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
        Optional<EmployeeReadDto> employeeRead = employeeService.findById(id);
        if(employeeRead.isEmpty())
            return "404";
        else
            model.addAttribute("employee", employeeRead.get());
        return "employee/show";
    }

    @GetMapping("/findByContractType/{contractType}")
    public String findByContractType(@PathVariable ContractType contractType, Model model){
        List<EmployeeReadDto> employeeList = employeeService.findByContractType(contractType);
        model.addAttribute("employeeList", employeeList);
        return "employee/showList";
    }
}
