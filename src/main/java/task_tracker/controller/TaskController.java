package task_tracker.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import task_tracker.domain.Attachment;
import task_tracker.domain.Comment;
import task_tracker.domain.Task;
import task_tracker.dto.*;
import task_tracker.enums.Priority;
import task_tracker.enums.TaskStatus;
import task_tracker.service.*;
import task_tracker.utils.Result;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/task")
@PreAuthorize("hasAuthority('ROLE_USER') || hasAuthority('ROLE_ADMIN')")
public class TaskController {
    private final TaskService taskService;
    private final UserService userService;
    private final ProjectService projectService;
    private final CommentService commentService;
    private final AttachmentService attachmentService;

    public TaskController(TaskService taskService, UserService userService, ProjectService projectService, CommentService commentService, AttachmentService attachmentService) {
        this.taskService = taskService;
        this.userService = userService;
        this.projectService = projectService;
        this.commentService = commentService;
        this.attachmentService = attachmentService;
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
    public String getNew(Model model, Principal principal) {
        Result<UserDto> userRead = userService.findByPrincipal(principal);
        if (userRead.isError()) {
            model.addAttribute("message", userRead.getMessage());
            return userRead.getCode();
        }
        UserDto userDto = userRead.getObject();
        model.addAttribute("userDto", userDto);
        model.addAttribute(new Task());
        model.addAttribute("taskStatus", TaskStatus.values());
        model.addAttribute("priority", Priority.values());
        return "task/new";
    }

    @GetMapping("/update/{id}")
    public String getUpdateForm(@PathVariable("id") UUID id, Model model, Principal principal) {
        Result<TaskDto> taskRead = taskService.findById(id);
        if (taskRead.isError()) {
            model.addAttribute("message", taskRead.getMessage());
            return taskRead.getCode();
        }
        Result<UserDto> userRead = userService.findByPrincipal(principal);
        if (userRead.isError()) {
            model.addAttribute("message", userRead.getMessage());
            return userRead.getCode();
        }
        UserDto userDto = userRead.getObject();
        model.addAttribute("userDto", userDto);
        model.addAttribute("task", taskRead.getObject());
        model.addAttribute("taskStatus", TaskStatus.values());
        model.addAttribute("priorities", Priority.values());
        return "task/update";
    }

    @GetMapping("/{id}")
    public String getTask(@PathVariable("id") UUID taskId, Principal principal, Model model) {
        Result<UserDto> userRead = userService.findByPrincipal(principal);
        if (userRead.isError()) {
            model.addAttribute("message", userRead.getMessage());
            return userRead.getCode();
        }
        UserDto userDto = userRead.getObject();
        Result<TaskDto> taskRead = taskService.findById(taskId);
        if (taskRead.isError()) {
            model.addAttribute("message", taskRead.getMessage());
            return taskRead.getCode();
        }

        TaskDto taskDto = taskRead.getObject();
        if (taskDto.getProjectId() != null) {
            Result<ProjectDto> projectRead = projectService.findById(taskDto.getProjectId());
            if (projectRead.isError()) {
                model.addAttribute("message", projectRead.getMessage());
                return projectRead.getCode();
            }
            ProjectDto projectDto = projectRead.getObject();
            model.addAttribute("projectName", projectDto.getName());
        }

        Result<List<CommentDto>> taskComments = commentService.findByTaskId(taskId);
        if (taskComments.isOk()) {
            List<CommentDto> comments = taskComments.getObject();
            model.addAttribute("comments", comments);
        }

        Result<List<AttachmentDto>> attachmentRead = attachmentService.findByTaskId(taskId);
        if (attachmentRead.isOk()) {
            List<AttachmentDto> attachmentDto = attachmentRead.getObject();
            model.addAttribute("attachments", attachmentDto);
        }

        model.addAttribute("taskDto", taskDto);
        model.addAttribute("userDto", userDto);
        model.addAttribute("newComment", new Comment());
        model.addAttribute("newAttachment", new Attachment());
        model.addAttribute("priorities", Priority.values());
        return "task/index";
    }

    @PostMapping("/")
    public String create(@ModelAttribute("task") Task task, Model model) {
        
        Result<TaskDto> savedTask = taskService.create(task);
        if (savedTask.isError()) {
            model.addAttribute("message", savedTask.getMessage());
            return savedTask.getCode();
        } else {
            model.addAttribute("message", "Task create ok");
            return "redirect:/";
        }
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
            return "redirect:/";
        }
    }
}
