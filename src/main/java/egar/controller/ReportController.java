package egar.controller;

import egar.domain.report.dto.ReportDtoRead;
import egar.domain.report.entity.Report;
import egar.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
//@RequestMapping("/reports") //TODO раскоментить
public class ReportController {
    private final ReportService reportService;

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Integer id, Model model){
        Optional<ReportDtoRead> reportRead = reportService.findById(id);
        if(reportRead.isEmpty())
            return "404";
        else
            model.addAttribute("report", reportRead.get());
        return "show";
    }

    @PostMapping("/")
    public String create(@ModelAttribute("report") Report report, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return "500";
        reportService.create(report);
        return "200";
    }
}
