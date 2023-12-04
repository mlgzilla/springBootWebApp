package egar.controller;

import egar.domain.report.dto.ReportDtoRead;
import egar.domain.report.entity.Report;
import egar.service.ReportService;
import egar.utils.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/report")
public class ReportController {
    private final ReportService reportService;

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Integer id, Model model){
        Result<ReportDtoRead> reportRead = reportService.findById(id);
        if(reportRead.isError())
            return "404";
        else
            model.addAttribute("report", reportRead.getObject());
        return "report/show";
    }

    @PostMapping("/")
    public String create(@ModelAttribute("report") Report report, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return "400";
        if(reportService.create(report).isEmpty())
            return "500";
        return "200";
    }

    @GetMapping("/new")
    public String getNew(Model model){
        model.addAttribute(new Report());
        return "report/new";
    }

    @GetMapping("/findByTaskId/{id}")
    public String findByTaskId(@PathVariable Integer id, Model model){
        List<ReportDtoRead> reportList = reportService.findByTaskId(id);
        model.addAttribute("reportList", reportList);
        return "report/showList";
    }
}
