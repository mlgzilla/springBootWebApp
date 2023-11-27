package egar.controller;

import egar.domain.employee.dto.EmployeeDtoRead;
import egar.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/document")
public class DocumentController {
    private DocumentService documentService;

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Integer id, Model model){
        Optional<EmployeeDtoRead> employeeRead = employeeService.findById(id);
        if(employeeRead.isEmpty())
            return "404";
        else
            model.addAttribute("employee", employeeRead.get());
        return "employee/show";
    }
}
