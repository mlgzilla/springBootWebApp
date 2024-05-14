package task_tracker.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import task_tracker.dto.ProjectDto;
import task_tracker.dto.UserDto;
import task_tracker.service.ProjectService;
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

    private final ProjectService projectService;

    @GetMapping("/update/{id}")
    public String getUpdateForm(@PathVariable("id") UUID id, Model model) {
        Result<UserDto> userRead = userService.findById(id);
        if (userRead.isError()) {
            model.addAttribute("message", userRead.getMessage());
            return userRead.getCode();
        }
        model.addAttribute("user", userRead.getObject());
        String searchQuery = "";
        model.addAttribute("searchQuery", searchQuery);
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

            String searchQuery = "";
            model.addAttribute("searchQuery", searchQuery);
        }
        return "user/index";
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
