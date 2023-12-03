package egar.controller;

import egar.domain.work_hours.dto.WorkHoursDtoRead;
import egar.domain.work_hours.entity.WorkHours;
import egar.service.WorkHoursService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/workHours")
public class WorkHoursController {
    private final WorkHoursService workHoursService;

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Integer id, Model model) {
        Optional<WorkHoursDtoRead> workHoursRead = workHoursService.findById(id);
        if (workHoursRead.isEmpty())
            return "404";
        model.addAttribute("workHours", workHoursRead.get());
        return "workHours/show";
    }

    @PostMapping("/")
    public String create(@ModelAttribute("workHours") WorkHours workHours, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "400";
        if (workHoursService.create(workHours).isEmpty())
            return "500";
        return "200";
    }

    @GetMapping("/new")
    public String getNew(Model model) {
        model.addAttribute(new WorkHours());
        return "workHours/new";
    }

    @GetMapping("/findByEmployeeId/{id}")
    public String findByEmployeeId(@PathVariable Integer id, Model model) {
        List<WorkHoursDtoRead> workHoursList = workHoursService.findByEmployeeId(id);
        model.addAttribute("workHoursList", workHoursList);
        return "workHours/showList";
    }

    @GetMapping("/findByEmployeeIdInRange/{id}{timeStart}{timeFinish}")
    public String findByEmployeeIdInRange(@PathVariable Integer id, @PathVariable LocalDateTime timeStart, @PathVariable LocalDateTime timeFinish, Model model) {
        List<WorkHoursDtoRead> workHoursList = workHoursService.findByEmployeeIdInRange(id, timeStart, timeFinish);
        model.addAttribute("workHoursList", workHoursList);
        return "workHours/showList";
    }
}
