package task_tracker.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import task_tracker.domain.Project;
import task_tracker.dto.ProjectDto;
import task_tracker.dto.TaskDto;
import task_tracker.dto.UserDto;
import task_tracker.service.ProjectService;
import task_tracker.service.TaskService;
import task_tracker.service.UserService;
import task_tracker.utils.Result;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/project")
public class ProjectController {
    private final ProjectService projectService;
    private final UserService userService;
    private final TaskService taskService;

    @GetMapping("/{id}")
    public String getProject(@PathVariable("id") UUID projectId, Principal principal, Model model) {
        Result<UserDto> userRead = userService.findByPrincipal(principal);
        if (userRead.isError()) {
            model.addAttribute("message", userRead.getMessage());
            return userRead.getCode();
        } else {
            UserDto userDto = userRead.getObject();
            Result<ProjectDto> projectRead = projectService.findById(projectId);
            if (projectRead.isError()) {
                model.addAttribute("message", projectRead.getMessage());
                return projectRead.getCode();
            } else {
                model.addAttribute("userName", userDto.getName());
                ProjectDto projectDto = projectRead.getObject();
                Set<TaskDto> tasks = projectDto.getTasks()
                        .stream()
                        .map(id -> taskService.findById(id).getObject())
                        .collect(Collectors.toSet());
                model.addAttribute("tasks", tasks);
            }
        }
        return "index";
    }

    @GetMapping("/new")
    public String getNew(Model model) {
        model.addAttribute(new Project());
        return "project/new";
    }

    @GetMapping("/update/{id}")
    public String getUpdateForm(@PathVariable("id") UUID id, Model model) {
        Result<ProjectDto> projectRead = projectService.findById(id);
        if (projectRead.isError()) {
            model.addAttribute("message", projectRead.getMessage());
            return projectRead.getCode();
        }
        model.addAttribute("project", projectRead.getObject());
        return "project/update";
    }

    @GetMapping("/")
    public String getHome(Principal principal, Model model) {
        Result<UserDto> userRead = userService.findByPrincipal(principal);
        if (userRead.isError()) {
            model.addAttribute("message", userRead.getMessage());
            return userRead.getCode();
        } else {
            UserDto userDto = userRead.getObject();
            Set<UUID> projects = userDto.getProjects();
            List<ProjectDto> projectList = projects.stream()
                    .map(id -> projectService.findById(id).getObject())
                    .toList();
            model.addAttribute("userName", userDto.getName());
            model.addAttribute("projectList", projectList);
        }
        return "project/index";
    }

    @PostMapping("/")
    public String create(@ModelAttribute("project") Project project, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("message", "Error in filled fields");
            return "400";
        }
        Result<ProjectDto> savedProject = projectService.create(project);
        if (savedProject.isError()) {
            model.addAttribute("message", savedProject.getMessage());
            return savedProject.getCode();
        } else
            return "redirect:/project/" + savedProject.getObject().getId();
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
            return "redirect:/project/" + id;
        if (employeeId != null) {
            if (inDateRange)
                return "redirect:/project/findByUserIdInRange/" + employeeId + "/" + dateStart + "/" + dateFinish;
            else
                return "redirect:/project/findByUserId/" + employeeId;
        }
        return "redirect:/project/";
    }

    @PutMapping("/{id}")
    public String update(@ModelAttribute("name") String name, @PathVariable("id") UUID id, Model model) {
        Result<String> upload = projectService.update(id, name);
        if (upload.isError()) {
            model.addAttribute("message", upload.getMessage());
            return upload.getCode();
        } else
            return "redirect:/project/" + id;
    }

    @PutMapping("/updateUsers/{id}")
    public String updateUsers(@ModelAttribute("userId") UUID userId, @PathVariable("id") UUID id, Model model) {
        Result<String> upload = projectService.addUser(id, userId);
        if (upload.isError()) {
            model.addAttribute("message", upload.getMessage());
            return upload.getCode();
        } else
            return "redirect:/project/" + id;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") UUID id, Model model) {
        Result<String> delete = projectService.delete(id);
        if (delete.isError()) {
            model.addAttribute("message", delete.getMessage());
            return delete.getCode();
        } else {
            model.addAttribute("message", delete.getObject());
            return "redirect:/project/";
        }
    }
}
