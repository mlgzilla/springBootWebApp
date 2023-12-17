package egar.controller;

import egar.domain.work_hours.dto.WorkHoursDtoRead;
import egar.domain.work_hours.entity.WorkHours;
import egar.service.WorkHoursService;
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
@RequestMapping("/workHours")
public class WorkHoursController {
    private final WorkHoursService workHoursService;

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Integer id, Model model) {
        Result<WorkHoursDtoRead> workHoursRead = workHoursService.findById(id);
        if (workHoursRead.isError()) {
            model.addAttribute("message", workHoursRead.getMessage());
            return workHoursRead.getCode();
        } else
            model.addAttribute("workHours", workHoursRead.getObject());
        return "workHours/show";
    }

    @GetMapping("/findByEmployeeId/{id}")
    public String findByEmployeeId(@PathVariable Integer id, Model model) {
        Result<List<WorkHoursDtoRead>> workHoursList = workHoursService.findByEmployeeId(id);
        if (workHoursList.isError()) {
            model.addAttribute("message", workHoursList.getMessage());
            return workHoursList.getCode();
        } else
            model.addAttribute("workHoursList", workHoursList.getObject());
        return "workHours/showList";
    }

    @GetMapping("/findByEmployeeIdInRange/{id}/{timeStart}/{timeFinish}")
    public String findByEmployeeIdInRange(@PathVariable Integer id, @PathVariable LocalDateTime timeStart, @PathVariable LocalDateTime timeFinish, Model model) {
        Result<List<WorkHoursDtoRead>> workHoursList = workHoursService.findByEmployeeIdInRange(id, timeStart, timeFinish);
        if (workHoursList.isError()) {
            model.addAttribute("message", workHoursList.getMessage());
            return workHoursList.getCode();
        } else
            model.addAttribute("workHoursList", workHoursList.getObject());
        return "workHours/showList";
    }

    @GetMapping("/new")
    public String getNew(Model model) {
        model.addAttribute(new WorkHours());
        return "workHours/new";
    }

    @GetMapping("/update/{id}")
    public String getUpdateForm(@PathVariable("id") Integer id, Model model) {
        Result<WorkHoursDtoRead> workHoursRead = workHoursService.findById(id);
        if (workHoursRead.isError()) {
            model.addAttribute("message", workHoursRead.getMessage());
            return workHoursRead.getCode();
        }
        model.addAttribute("workHours", workHoursRead.getObject());
        return "workHours/update";
    }

    @GetMapping("/")
    public String getHome() {
        return "workHours/index";
    }

    @PostMapping("/")
    public String create(@ModelAttribute("workHours") WorkHours workHours, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("message", "Error in filled fields");
            return "400";
        }
        Result<WorkHoursDtoRead> savedWorkHours = workHoursService.create(workHours);
        if (savedWorkHours.isError()) {
            model.addAttribute("message", savedWorkHours.getMessage());
            return savedWorkHours.getCode();
        } else
            return "redirect:/workHours/" + savedWorkHours.getObject().getId();
    }

    @PostMapping("/submit")
    public String inputSubmit(
            @RequestParam(required = false, name = "id") Integer id,
            @RequestParam(required = false, name = "employeeId") Integer employeeId,
            @RequestParam(required = false, name = "inDateRange") boolean inDateRange,
            @RequestParam(required = false, name = "dateStart") LocalDateTime dateStart,
            @RequestParam(required = false, name = "dateFinish") LocalDateTime dateFinish
    ) {
        if (id != null)
            return "redirect:/workHours/" + id;
        if (employeeId != null) {
            if (inDateRange)
                return "redirect:/workHours/findByEmployeeIdInRange/" + employeeId + "/" + dateStart + "/" + dateFinish;
            else
                return "redirect:/workHours/findByEmployeeId/" + employeeId;
        }
        return "redirect:/workHours/";
    }

    @PutMapping("/{id}")
    public String update(@ModelAttribute("workHours") WorkHoursDtoRead workHours, @PathVariable("id") Integer id, Model model) {
        Result<String> upload = workHoursService.update(id, workHours);
        if (upload.isError()) {
            model.addAttribute("message", upload.getMessage());
            return upload.getCode();
        } else
            return "redirect:/workHours/" + id;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Integer id, Model model) {
        Result<String> delete = workHoursService.delete(id);
        if (delete.isError()) {
            model.addAttribute("message", delete.getMessage());
            return delete.getCode();
        } else {
            model.addAttribute("message", delete.getObject());
            return "redirect:/workHours/";
        }
    }
}
