package egar.controller;

import egar.domain.employee.dto.EmployeeDtoRead;
import egar.domain.task.entity.Task;
import egar.enums.TaskStatus;
import egar.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/document")
public class DocumentController {
    private final DocumentService documentService;

//    @GetMapping("/{id}")
//    public String findById(@PathVariable("id") Integer id, Model model){
//        Optional<EmployeeDtoRead> employeeRead = employeeService.findById(id);
//        if(employeeRead.isEmpty())
//            return "404";
//        else
//            model.addAttribute("employee", employeeRead.get());
//        return "employee/show";
//    }

    @GetMapping("/upload")
    public String getNew(Model model) {
        return "document/upload";
    }

    @PostMapping("/")
    public String create(@ModelAttribute("file") MultipartFile file) {
        if (documentService.upload(file).isEmpty())
            return "500";
        return "200";
    }

}
