package task_tracker.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
@RequestMapping("/workTime")
public class WorkTimeController {
    private final WorkTimeService workTimeService;

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") UUID id, Model model) {
        Result<WorkTimeDto> workTimeRead = workTimeService.findById(id);
        if (workTimeRead.isError()) {
            model.addAttribute("message", workTimeRead.getMessage());
            return workTimeRead.getCode();
        } else
            model.addAttribute("workTime", workTimeRead.getObject());
        return "workTime/show";
    }

    @GetMapping("/findByUserId/{id}")
    public String findByUserId(@PathVariable UUID id, Model model) {
        Result<List<WorkTimeDto>> workTimeList = workTimeService.findByUserId(id);
        if (workTimeList.isError()) {
            model.addAttribute("message", workTimeList.getMessage());
            return workTimeList.getCode();
        } else
            model.addAttribute("workTimeList", workTimeList.getObject());
        return "workTime/showList";
    }

    @GetMapping("/findByUserIdInRange/{id}/{timeStart}/{timeFinish}")
    public String findByUserIdInRange(@PathVariable UUID id, @PathVariable LocalDateTime timeStart, @PathVariable LocalDateTime timeFinish, Model model) {
        Result<List<WorkTimeDto>> workTimeList = workTimeService.findByUserIdInRange(id, timeStart, timeFinish);
        if (workTimeList.isError()) {
            model.addAttribute("message", workTimeList.getMessage());
            return workTimeList.getCode();
        } else
            model.addAttribute("workTimeList", workTimeList.getObject());
        return "workTime/showList";
    }

    @GetMapping("/new")
    public String getNew(Model model) {
        model.addAttribute(new WorkTime());
        return "workTime/new";
    }

    @GetMapping("/update/{id}")
    public String getUpdateForm(@PathVariable("id") UUID id, Model model) {
        Result<WorkTimeDto> workTimeRead = workTimeService.findById(id);
        if (workTimeRead.isError()) {
            model.addAttribute("message", workTimeRead.getMessage());
            return workTimeRead.getCode();
        }
        model.addAttribute("workTime", workTimeRead.getObject());
        return "workTime/update";
    }

    @GetMapping("/")
    public String getHome() {
        return "workTime/index";
    }

    @PostMapping("/")
    public String create(@ModelAttribute("workTime") WorkTime workTime, Model model) {

        Result<WorkTimeDto> savedWorkTime = workTimeService.create(workTime);
        if (savedWorkTime.isError()) {
            model.addAttribute("message", savedWorkTime.getMessage());
            return savedWorkTime.getCode();
        } else
            return "redirect:/workTime/" + savedWorkTime.getObject().getId();
    }

    @PutMapping("/{id}")
    public String update(@ModelAttribute("workTime") WorkTimeDto workTime, @PathVariable("id") UUID id, Model model) {
        Result<String> upload = workTimeService.update(id, workTime);
        if (upload.isError()) {
            model.addAttribute("message", upload.getMessage());
            return upload.getCode();
        } else
            return "redirect:/workTime/" + id;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") UUID id, Model model) {
        Result<String> delete = workTimeService.delete(id);
        if (delete.isError()) {
            model.addAttribute("message", delete.getMessage());
            return delete.getCode();
        } else {
            model.addAttribute("message", delete.getObject());
            return "redirect:/workTime/";
        }
    }
}
