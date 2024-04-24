package task_tracker.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import task_tracker.domain.WorkTime;
import task_tracker.dto.WorkTimeDto;
import task_tracker.service.WorkTimeService;
import task_tracker.utils.Result;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/workHours")
public class WorkTimeController {
    private final WorkTimeService workTimeService;

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") UUID id, Model model) {
        Result<WorkTimeDto> workHoursRead = workTimeService.findById(id);
        if (workHoursRead.isError()) {
            model.addAttribute("message", workHoursRead.getMessage());
            return workHoursRead.getCode();
        } else
            model.addAttribute("workHours", workHoursRead.getObject());
        return "workHours/show";
    }

    @GetMapping("/findByUserId/{id}")
    public String findByUserId(@PathVariable UUID id, Model model) {
        Result<List<WorkTimeDto>> workHoursList = workTimeService.findByUserId(id);
        if (workHoursList.isError()) {
            model.addAttribute("message", workHoursList.getMessage());
            return workHoursList.getCode();
        } else
            model.addAttribute("workHoursList", workHoursList.getObject());
        return "workHours/showList";
    }

    @GetMapping("/findByUserIdInRange/{id}/{timeStart}/{timeFinish}")
    public String findByUserIdInRange(@PathVariable UUID id, @PathVariable LocalDateTime timeStart, @PathVariable LocalDateTime timeFinish, Model model) {
        Result<List<WorkTimeDto>> workHoursList = workTimeService.findByUserIdInRange(id, timeStart, timeFinish);
        if (workHoursList.isError()) {
            model.addAttribute("message", workHoursList.getMessage());
            return workHoursList.getCode();
        } else
            model.addAttribute("workHoursList", workHoursList.getObject());
        return "workHours/showList";
    }

    @GetMapping("/new")
    public String getNew(Model model) {
        model.addAttribute(new WorkTime());
        return "workHours/new";
    }

    @GetMapping("/update/{id}")
    public String getUpdateForm(@PathVariable("id") UUID id, Model model) {
        Result<WorkTimeDto> workHoursRead = workTimeService.findById(id);
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
    public String create(@ModelAttribute("workHours") WorkTime workTime, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("message", "Error in filled fields");
            return "400";
        }
        Result<WorkTimeDto> savedWorkHours = workTimeService.create(workTime);
        if (savedWorkHours.isError()) {
            model.addAttribute("message", savedWorkHours.getMessage());
            return savedWorkHours.getCode();
        } else
            return "redirect:/workHours/" + savedWorkHours.getObject().getId();
    }

    @PostMapping("/submit")
    public String inputSubmit(
            @RequestParam(required = false, name = "id") UUID id,
            @RequestParam(required = false, name = "employeeId") UUID employeeId,
            @RequestParam(required = false, name = "inDateRange") boolean inDateRange,
            @RequestParam(required = false, name = "dateStart") LocalDateTime dateStart,
            @RequestParam(required = false, name = "dateFinish") LocalDateTime dateFinish
    ) {
        if (id != null)
            return "redirect:/workHours/" + id;
        if (employeeId != null) {
            if (inDateRange)
                return "redirect:/workHours/findByUserIdInRange/" + employeeId + "/" + dateStart + "/" + dateFinish;
            else
                return "redirect:/workHours/findByUserId/" + employeeId;
        }
        return "redirect:/workHours/";
    }

    @PutMapping("/{id}")
    public String update(@ModelAttribute("workHours") WorkTimeDto workHours, @PathVariable("id") UUID id, Model model) {
        Result<String> upload = workTimeService.update(id, workHours);
        if (upload.isError()) {
            model.addAttribute("message", upload.getMessage());
            return upload.getCode();
        } else
            return "redirect:/workHours/" + id;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") UUID id, Model model) {
        Result<String> delete = workTimeService.delete(id);
        if (delete.isError()) {
            model.addAttribute("message", delete.getMessage());
            return delete.getCode();
        } else {
            model.addAttribute("message", delete.getObject());
            return "redirect:/workHours/";
        }
    }
}
