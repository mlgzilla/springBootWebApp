package task_tracker.controller;

import task_tracker.dto.ReportDto;
import task_tracker.domain.Report;
import task_tracker.service.ReportService;
import task_tracker.utils.Result;
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
        Result<ReportDto> reportRead = reportService.findById(id);
        if (reportRead.isError()) {
            model.addAttribute("message", reportRead.getMessage());
            return reportRead.getCode();
        } else
            model.addAttribute("report", reportRead.getObject());
        return "report/show";
    }

    @GetMapping("/findByTaskId/{id}")
    public String findByTaskId(@PathVariable Integer id, Model model) {
        Result<List<ReportDto>> reportList = reportService.findByTaskId(id);
        if (reportList.isError()) {
            model.addAttribute("message", reportList.getMessage());
            return reportList.getCode();
        } else
            model.addAttribute("reportList", reportList.getObject());
        return "report/showList";
    }

    @GetMapping("/findByTaskIdInRange/{id}/{timeStart}/{timeFinish}")
    public String findByTaskIdInRange(@PathVariable Integer id, @PathVariable LocalDateTime timeStart, @PathVariable LocalDateTime timeFinish, Model model) {
        Result<List<ReportDto>> reportList = reportService.findByTaskIdInRange(id, timeStart, timeFinish);
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

    @GetMapping("/update/{id}")
    public String getUpdateForm(@PathVariable("id") Integer id, Model model) {
        Result<ReportDto> reportRead = reportService.findById(id);
        if (reportRead.isError()) {
            model.addAttribute("message", reportRead.getMessage());
            return reportRead.getCode();
        }
        model.addAttribute("report", reportRead.getObject());
        return "report/update";
    }

    @GetMapping("/")
    public String getHome() {
        return "report/index";
    }

    @PostMapping("/")
    public String create(@ModelAttribute("report") Report report, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("message", "Error in filled fields");
            return "400";
        }
        Result<ReportDto> savedReport = reportService.create(report);
        if (savedReport.isError()) {
            model.addAttribute("message", savedReport.getMessage());
            return savedReport.getCode();
        } else {
            model.addAttribute("message", "Report create ok");
            return "redirect:/report/" + savedReport.getObject().getId();
        }
    }

    @PostMapping("/submit")
    public String inputSubmit(
            @RequestParam(required = false, name = "id") Integer id,
            @RequestParam(required = false, name = "taskId") Integer taskId,
            @RequestParam(required = false, name = "inDateRange") boolean inDateRange,
            @RequestParam(required = false, name = "dateStart") LocalDateTime dateStart,
            @RequestParam(required = false, name = "dateFinish") LocalDateTime dateFinish
    ) {
        if (id != null)
            return "redirect:/report/" + id;
        if (taskId != null) {
            if (inDateRange)
                return "redirect:/report/findByTaskIdInRange/" + taskId + "/" + dateStart + "/" + dateFinish;
            else
                return "redirect:/report/findByTaskId/" + taskId;
        }
        return "redirect:/report/";
    }

    @PutMapping("/{id}")
    public String update(@ModelAttribute("report") ReportDto report, @PathVariable("id") Integer id, Model model) {
        Result<String> upload = reportService.update(id, report);
        if (upload.isError()) {
            model.addAttribute("message", upload.getMessage());
            return upload.getCode();
        } else
            return "redirect:/report/" + id;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Integer id, Model model) {
        Result<String> delete = reportService.delete(id);
        if (delete.isError()) {
            model.addAttribute("message", delete.getMessage());
            return delete.getCode();
        } else {
            model.addAttribute("message", delete.getObject());
            return "redirect:/report/";
        }
    }
}
