package egar.controller;

import egar.domain.report.dto.ReportDtoRead;
import egar.domain.task.dto.TaskDtoRead;
import egar.domain.task.entity.Task;
import egar.enums.TaskStatus;
import egar.service.TaskService;
import egar.utils.Result;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/task")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Integer id, Model model) {
        Result<TaskDtoRead> taskRead = taskService.findById(id);
        if (taskRead.isError()) {
            model.addAttribute("message", taskRead.getMessage());
            return taskRead.getCode();
        } else
            model.addAttribute("task", taskRead.getObject());
        return "task/show";
    }

    @GetMapping("/findByEmployeeId/{id}")
    public String findByEmployeeId(@PathVariable Integer id, Model model) {
        Result<List<TaskDtoRead>> taskList = taskService.findByEmployeeId(id);
        if (taskList.isError()) {
            model.addAttribute("message", taskList.getMessage());
            return taskList.getCode();
        } else
            model.addAttribute("taskList", taskList.getObject());
        return "task/showList";
    }

    @GetMapping("/findByStatus/{status}")
    public String findByStatus(@PathVariable TaskStatus status, Model model) {
        Result<List<TaskDtoRead>> taskList = taskService.findByStatus(status);
        if (taskList.isError()) {
            model.addAttribute("message", taskList.getMessage());
            return taskList.getCode();
        } else
            model.addAttribute("taskList", taskList.getObject());
        return "task/showList";
    }

    @GetMapping("/findWithReportsById/{id}")
    public String findWithReportsById(@PathVariable Integer id, Model model) {
        Result<TaskDtoRead> taskRead = taskService.findById(id);
        if (taskRead.isError()) {
            model.addAttribute("message", taskRead.getMessage());
            return taskRead.getCode();
        } else {
            TaskDtoRead task = taskRead.getObject();
            Result<List<ReportDtoRead>> reports = taskService.findReportsByTaskId(id);
            if (reports.isError()) {
                if (reports.getCode().equals("404")) {
                    model.addAttribute("task", task);
                    return "task/show";
                } else {
                    model.addAttribute("message", reports.getMessage());
                    return reports.getCode();
                }
            } else {
                task.setReports(reports.getObject().stream().collect(Collectors.toSet()));
                model.addAttribute("task", task);
                return "task/show";
            }
        }
    }

    @GetMapping("/new")
    public String getNew(Model model) {
        model.addAttribute(new Task());
        model.addAttribute("taskStatus", TaskStatus.values());
        return "task/new";
    }

    @GetMapping("/update/{id}")
    public String getUpdateForm(@PathVariable("id") Integer id, Model model) {
        Result<TaskDtoRead> taskRead = taskService.findById(id);
        if (taskRead.isError()) {
            model.addAttribute("message", taskRead.getMessage());
            return taskRead.getCode();
        }
        model.addAttribute("task", taskRead.getObject());
        return "task/update";
    }

    @GetMapping("/")
    public String getHome() {
        return "task/index";
    }

    @PostMapping("/")
    public String create(@ModelAttribute("task") Task task, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("message", "Error in filled fields");
            return "400";
        }
        Result<TaskDtoRead> savedTask = taskService.create(task);
        if (savedTask.isError()) {
            model.addAttribute("message", savedTask.getMessage());
            return savedTask.getCode();
        } else {
            model.addAttribute("message", "Task create ok");
            return "redirect:/task/" + savedTask.getObject().getId();
        }
    }

    @PostMapping("/submit")
    public String inputSubmit(
            @RequestParam(required = false, name = "id") Integer id,
            @RequestParam(required = false, name = "employeeId") Integer employeeId,
            @RequestParam(required = false, name = "status") String status,
            @RequestParam(required = false, name = "includeReports") boolean includeReports
    ) {
        if (id != null) {
            if (includeReports)
                return "redirect:/task/findWithReportsById/" + id;
            else
                return "redirect:/task/" + id;
        }
        if (employeeId != null)
            return "redirect:/task/findByEmployeeId/" + employeeId;
        if (status != null)
            return "redirect:/task/findByStatus/" + status;

        return "redirect:/task/";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable("id") Integer id, @ModelAttribute("task") TaskDtoRead task, Model model) {
        Result<String> upload = taskService.update(id, task);
        if (upload.isError()) {
            model.addAttribute("message", upload.getMessage());
            return upload.getCode();
        } else
            return "redirect:/task/" + id;
    }

    @GetMapping("/updateStatus/{id}/{status}")
    public String updateStatus(@PathVariable("id") Integer id, @PathVariable("status") TaskStatus taskStatus, Model model) {
        Result<String> upload = taskService.updateStatus(id, taskStatus);
        if (upload.isError()) {
            model.addAttribute("message", upload.getMessage());
            return upload.getCode();
        } else
            return "redirect:/task/" + id;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Integer id, Model model) {
        Result<String> delete = taskService.delete(id);
        if (delete.isError()) {
            model.addAttribute("message", delete.getMessage());
            return delete.getCode();
        } else {
            model.addAttribute("message", delete.getObject());
            return "redirect:/task/";
        }
    }
}
