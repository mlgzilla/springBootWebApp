package egar.controller;

import egar.domain.report.dto.ReportDtoRead;
import egar.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
//@RequestMapping("/reports")
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
}
