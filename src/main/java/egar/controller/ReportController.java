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

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/report")
public class ReportController {
    private final ReportService reportService;

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Integer id, Model model) {
        Result<ReportDtoRead> reportRead = reportService.findById(id);
        if (reportRead.isError()) {
            model.addAttribute("message", reportRead.getMessage());
            return reportRead.getCode();
        } else
            model.addAttribute("report", reportRead.getObject());
        return "report/show";
    }

    @GetMapping("/findByTaskId/{id}")
    public String findByTaskId(@PathVariable Integer id, Model model) {
        Result<List<ReportDtoRead>> reportList = reportService.findByTaskId(id);
        if (reportList.isError()) {
            model.addAttribute("message", reportList.getMessage());
            return reportList.getCode();
        } else
            model.addAttribute("reportList", reportList.getObject());
        return "report/showList";
    }

    @GetMapping("/findByTaskId/{id}{timeStart}{timeFinish}")
    public String findByTaskIdInRange(@PathVariable Integer id, @PathVariable LocalDateTime timeStart, @PathVariable LocalDateTime timeFinish, Model model) {
        Result<List<ReportDtoRead>> reportList = reportService.findByTaskIdInRange(id, timeStart, timeFinish);
        if (reportList.isError()) {
            model.addAttribute("message", reportList.getMessage());
            return reportList.getCode();
        } else
            model.addAttribute("reportList", reportList.getObject());
        return "report/showList";
    }

    @GetMapping("/new")
    public String getNew(Model model) {
        model.addAttribute(new Report());
        return "report/new";
    }

    @PostMapping("/")
    public String create(@ModelAttribute("report") Report report, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("message", "Error in filled fields");
            return "400";
        }
        Result<ReportDtoRead> savedReport = reportService.create(report);
        if (savedReport.isError()) {
            model.addAttribute("message", savedReport.getMessage());
            return savedReport.getCode();
        } else {
            model.addAttribute("message", "Report create ok");
            return "200";
        }
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Integer id, Model model) {
        Result<String> delete = reportService.delete(id);
        if (delete.isError()) {
            model.addAttribute("message", delete.getMessage());
            return delete.getCode();
        } else {
            model.addAttribute("message", delete.getObject());
            return "200";
        }
    }
}
