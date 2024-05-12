package task_tracker.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import task_tracker.domain.Attachment;
import task_tracker.domain.Comment;
import task_tracker.domain.User;
import task_tracker.dto.*;
import task_tracker.enums.ContractType;
import task_tracker.enums.Priority;
import task_tracker.service.ProjectService;
import task_tracker.service.TaskService;
import task_tracker.service.UserService;
import task_tracker.utils.Result;

import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    private final TaskService taskService;

    private final ProjectService projectService;

    @GetMapping("/findByName/{name}")
    public String findByName(@PathVariable String name, Model model) {
        Result<List<UserDto>> userList = userService.findByName(name);
        if (userList.isError()) {
            model.addAttribute("message", userList.getMessage());
            return userList.getCode();
        } else
            model.addAttribute("userList", userList.getObject());
        return "user/showList";
    }

    @GetMapping("/findBySurename/{surename}")
    public String findByMiddleName(@PathVariable String surename, Model model) {
        Result<List<UserDto>> userList = userService.findBySurename(surename);
        if (userList.isError()) {
            model.addAttribute("message", userList.getMessage());
            return userList.getCode();
        } else
            model.addAttribute("userList", userList.getObject());
        return "user/showList";
    }

    @GetMapping("/new")
    public String getNew(Model model) {
        model.addAttribute(new User());
        model.addAttribute("types", ContractType.values());
        return "user/new";
    }

    @GetMapping("/update/{id}")
    public String getUpdateForm(@PathVariable("id") UUID id, Model model) {
        Result<UserDto> userRead = userService.findById(id);
        if (userRead.isError()) {
            model.addAttribute("message", userRead.getMessage());
            return userRead.getCode();
        }
        model.addAttribute("user", userRead.getObject());
        return "user/update";
    }

    @GetMapping("/{id}")
    public String getUser(@PathVariable("id") UUID userId, Principal principal, Model model) {
        Result<UserDto> userRead = userService.findByPrincipal(principal);
        if (userRead.isError()) {
            model.addAttribute("message", userRead.getMessage());
            return userRead.getCode();
        }
        UserDto userDto = userRead.getObject();

        model.addAttribute("userDto", userDto);
        if (userDto.getId() == userId) {
            Set<UUID> projects = userDto.getProjects();
            List<ProjectDto> projectList = Collections.emptyList();
            if (projects != null) {
                projectList = projects.stream()
                        .map(id -> projectService.findById(id).getObject())
                        .toList();
            }
            model.addAttribute("projectList", projectList);
            model.addAttribute("userDtoFound", userDto);
        } else {
            Result<UserDto> userDtoRead = userService.findById(userId);
            if (userDtoRead.isError()) {
                model.addAttribute("message", userDtoRead.getMessage());
                return userDtoRead.getCode();
            }
            UserDto userDtoFound = userDtoRead.getObject();
            Set<UUID> projects = userDtoFound.getProjects();
            List<ProjectDto> projectList = Collections.emptyList();
            if (projects != null) {
                projectList = projects.stream()
                        .map(id -> projectService.findById(id).getObject())
                        .toList();
            }
            model.addAttribute("projectList", projectList);
            model.addAttribute("userDtoFound", userDtoFound);
        }
        return "user/index";
    }

    @PostMapping("/submit")
    public String inputSubmit(
            @RequestParam(required = false, name = "id") UUID id,
            @RequestParam(required = false, name = "firstName") String firstName,
            @RequestParam(required = false, name = "middleName") String middleName,
            @RequestParam(required = false, name = "secondName") String secondName,
            @RequestParam(required = false, name = "contractType") ContractType contractType
    ) {
        if (id != null)
            return "redirect:/user/" + id;
        if (firstName != null)
            return "redirect:/user/findByFirstName/" + firstName;
        if (middleName != null)
            return "redirect:/user/findByMiddleName/" + middleName;
        if (secondName != null)
            return "redirect:/user/findBySecondName/" + secondName;
        if (contractType != null)
            return "redirect:/user/findByContractType/" + contractType;

        return "redirect:/user/";
    }

    @PutMapping("/{id}")
    public String update(@ModelAttribute("user") UserDto user, @PathVariable("id") UUID id, Model model) {
        Result<String> upload = userService.update(id, user);
        if (upload.isError()) {
            model.addAttribute("message", upload.getMessage());
            return upload.getCode();
        } else
            return "redirect:/user/" + id;
    }

//    @PutMapping("/updateRoles/{id}")
//    public String updateRoles(@ModelAttribute("roleId") UUID roleId, @PathVariable("id") UUID id, Model model) {
//        Result<String> upload = userService.addRole(id, roleId);
//        if (upload.isError()) {
//            model.addAttribute("message", upload.getMessage());
//            return upload.getCode();
//        } else
//            return "redirect:/project/" + id;
//    }

    @PutMapping("/updatePassword/{id}")
    public String updatePassword(@ModelAttribute("roleId") String newPassword, @PathVariable("id") UUID id, Model model) {
        Result<String> upload = userService.updatePassword(id, newPassword);
        if (upload.isError()) {
            model.addAttribute("message", upload.getMessage());
            return upload.getCode();
        } else
            return "redirect:/project/" + id;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") UUID id, Model model) {
        Result<String> delete = userService.delete(id);
        if (delete.isError()) {
            model.addAttribute("message", delete.getMessage());
            return delete.getCode();
        } else {
            model.addAttribute("message", delete.getObject());
            return "redirect:/user/";
        }
    }
}
