package egar.controller;

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
        if (taskRead.isError())
            return "404";
        else
            model.addAttribute("task", taskRead.getObject());
        return "task/show";
    }

    @PostMapping("/")
    public String create(@ModelAttribute("task") Task task, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "400";
        if (taskService.create(task).isEmpty())
            return "500";
        return "200";
    }

    @GetMapping("/new")
    public String getNew(Model model) {
        model.addAttribute(new Task());
        model.addAttribute("taskStatus", TaskStatus.values());
        return "task/new";
    }

    @GetMapping("/findByEmployeeId/{id}")
    public String findByEmployeeId(@PathVariable Integer id, Model model) {
        List<TaskDtoRead> taskList = taskService.findByEmployeeId(id);
        model.addAttribute("taskList", taskList);
        return "task/showList";
    }

    @GetMapping("/findByStatus/{status}")
    public String findByStatus(@PathVariable TaskStatus status, Model model) {
        List<TaskDtoRead> taskList = taskService.findByStatus(status);
        model.addAttribute("taskList", taskList);
        return "task/showList";
    }

    @GetMapping("/findWithReportsById/{id}")
    public String findWithReportsById(@PathVariable Integer id, Model model) {
        Result<TaskDtoRead> taskRead = taskService.findById(id);
        if (taskRead.isError())
            return "404";
        else {
            TaskDtoRead task = taskRead.getObject();
            task.setReports(taskService.findReportsByTaskId(id).stream().collect(Collectors.toSet()));
            model.addAttribute("task", task);
            return "task/show";
        }
    }
}
