package task_tracker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import task_tracker.domain.Task;
import task_tracker.dto.TaskDto;
import task_tracker.enums.Priority;
import task_tracker.enums.TaskStatus;
import task_tracker.service.TaskService;
import task_tracker.utils.Result;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/task")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") UUID id, Model model) {
        Result<TaskDto> taskRead = taskService.findById(id);
        if (taskRead.isError()) {
            model.addAttribute("message", taskRead.getMessage());
            return taskRead.getCode();
        } else
            model.addAttribute("task", taskRead.getObject());
        return "task/show";
    }

    @GetMapping("/findByUserId/{id}")
    public String findByUserId(@PathVariable UUID id, Model model) {
        Result<List<TaskDto>> taskList = taskService.findByUserId(id);
        if (taskList.isError()) {
            model.addAttribute("message", taskList.getMessage());
            return taskList.getCode();
        } else
            model.addAttribute("taskList", taskList.getObject());
        return "task/showList";
    }

    @GetMapping("/findByStatus/{status}")
    public String findByStatus(@PathVariable TaskStatus status, Model model) {
        Result<List<TaskDto>> taskList = taskService.findByStatus(status);
        if (taskList.isError()) {
            model.addAttribute("message", taskList.getMessage());
            return taskList.getCode();
        } else
            model.addAttribute("taskList", taskList.getObject());
        return "task/showList";
    }

    @GetMapping("/findByPriority/{priority}")
    public String findByPriority(@PathVariable Priority priority, Model model) {
        Result<List<TaskDto>> taskList = taskService.findByPriority(priority);
        if (taskList.isError()) {
            model.addAttribute("message", taskList.getMessage());
            return taskList.getCode();
        } else
            model.addAttribute("taskList", taskList.getObject());
        return "task/showList";
    }

    @GetMapping("/new")
    public String getNew(Model model) {
        model.addAttribute(new Task());
        model.addAttribute("taskStatus", TaskStatus.values());
        model.addAttribute("priority", Priority.values());
        return "task/new";
    }

    @GetMapping("/update/{id}")
    public String getUpdateForm(@PathVariable("id") UUID id, Model model) {
        Result<TaskDto> taskRead = taskService.findById(id);
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
        Result<TaskDto> savedTask = taskService.create(task);
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
            @RequestParam(required = false, name = "id") UUID id,
            @RequestParam(required = false, name = "employeeId") UUID employeeId,
            @RequestParam(required = false, name = "status") String status,
            @RequestParam(required = false, name = "includeComments") boolean includeComments
    ) {
        if (id != null) {
            if (includeComments)
                return "redirect:/task/findWithCommentsById/" + id;
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
    public String update(@PathVariable("id") UUID id, @ModelAttribute("task") TaskDto task, Model model) {
        Result<String> upload = taskService.update(id, task);
        if (upload.isError()) {
            model.addAttribute("message", upload.getMessage());
            return upload.getCode();
        } else
            return "redirect:/task/" + id;
    }

    @GetMapping("/updateStatus/{id}/{status}")
    public String updateStatus(@PathVariable("id") UUID id, @PathVariable("status") TaskStatus taskStatus, Model model) {
        Result<String> upload = taskService.updateStatus(id, taskStatus);
        if (upload.isError()) {
            model.addAttribute("message", upload.getMessage());
            return upload.getCode();
        } else
            return "redirect:/task/" + id;
    }

    @GetMapping("/updatePriority/{id}/{priority}")
    public String updatePriority(@PathVariable("id") UUID id, @PathVariable("priority") Priority priority, Model model) {
        Result<String> upload = taskService.updatePriority(id, priority);
        if (upload.isError()) {
            model.addAttribute("message", upload.getMessage());
            return upload.getCode();
        } else
            return "redirect:/task/" + id;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") UUID id, Model model) {
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
