package task_tracker.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import task_tracker.domain.User;
import task_tracker.dto.ProjectDto;
import task_tracker.dto.TaskDto;
import task_tracker.dto.UserDto;
import task_tracker.service.ProjectService;
import task_tracker.service.TaskService;
import task_tracker.service.UserService;
import task_tracker.utils.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final UserService userService;
    private final TaskService taskService;
    private final ProjectService projectService;

    @GetMapping("/")
    @PreAuthorize("hasAuthority('ROLE_USER') || hasAuthority('ROLE_ADMIN')")
    public String getHome(Principal principal, Model model) {
        Result<UserDto> userRead = userService.findByPrincipal(principal);
        if (userRead.isError()) {
            model.addAttribute("message", userRead.getMessage());
            return userRead.getCode();
        } else {
            UserDto userDto = userRead.getObject();
            Result<List<TaskDto>> taskRead = taskService.findByUserId(userDto.getId());
            if (taskRead.isError()) {
                model.addAttribute("message", taskRead.getMessage());
                return taskRead.getCode();
            } else {
                model.addAttribute("userDto", userDto);
                List<TaskDto> tasksDto = taskRead.getObject();
                model.addAttribute("tasks", tasksDto);

                String searchQuery = "";
                model.addAttribute("searchQuery", searchQuery);
            }
        }
        return "index";
    }

    @PostMapping("/search")
    @PreAuthorize("hasAuthority('ROLE_USER') || hasAuthority('ROLE_ADMIN')")
    public String getSearch(Principal principal, Model model, @ModelAttribute("searchQuery") String query) {
        Result<UserDto> userRead = userService.findByPrincipal(principal);
        if (userRead.isError()) {
            model.addAttribute("message", userRead.getMessage());
            return userRead.getCode();
        }
        Result<List<UserDto>> userNamesResult = userService.findByName(query);
        if (userNamesResult.isError()) {
            model.addAttribute("message", userNamesResult.getMessage());
            return userNamesResult.getCode();
        }
        Result<List<UserDto>> userSurenamesResult = userService.findBySurename(query);
        if (userSurenamesResult.isError()) {
            model.addAttribute("message", userSurenamesResult.getMessage());
            return userSurenamesResult.getCode();
        }
        Result<List<TaskDto>> tasksResult = taskService.findByName(query);
        if (tasksResult.isError()) {
            model.addAttribute("message", tasksResult.getMessage());
            return tasksResult.getCode();
        }
        Result<List<ProjectDto>> projectsResult = projectService.findByName(query);
        if (projectsResult.isError()) {
            model.addAttribute("message", projectsResult.getMessage());
            return projectsResult.getCode();
        }
        List<UserDto> userSurenames = userSurenamesResult.getObject();
        List<UserDto> userNames = userNamesResult.getObject();
        userNames.removeAll(userSurenames);
        userNames.addAll(userSurenames);
        model.addAttribute("usersList", userNames);
        model.addAttribute("tasksList", tasksResult.getObject());
        model.addAttribute("projectsList", projectsResult.getObject());
        UserDto userDto = userRead.getObject();
        model.addAttribute("userDto", userDto);
        String searchQuery = "";
        model.addAttribute("searchQuery", searchQuery);
        return "search/index";
    }

    @GetMapping("/signup")
    public String singUp(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping("/signup")
    public String singUp(User user, Model model) {
        if (userService.userExists(user.getLogin())) {
            model.addAttribute("error",
                    "Пользователь с логином: " + user.getLogin() + " уже существует");
            model.addAttribute(new User());
            return "signup";
        }
        if (user.getPassword().equals("")) {
            model.addAttribute("error", "Пароль должен быть указан");
            model.addAttribute(new User());
            return "signup";
        }
        if (user.getLogin().equals("")) {
            model.addAttribute("error", "Логин должен быть указан");
            model.addAttribute(new User());
            return "signup";
        }
        if (user.getName().equals("")) {
            model.addAttribute("error", "Имя должен быть указан");
            model.addAttribute(new User());
            return "signup";
        }
        if (user.getSurename().equals("")) {
            model.addAttribute("error", "Фамилия должен быть указан");
            model.addAttribute(new User());
            return "signup";
        }
        Result<User> result = userService.create(user);
        if (result.isError()) {
            model.addAttribute("error", "Не удалось создать пользователя ");
            model.addAttribute(new User());
            return "signup";
        }
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("errorMessage", "Неправильный логин или пароль");
        }
        return "login";
    }

    @PostMapping("/logout")
    @PreAuthorize("hasAuthority('ROLE_USER') || hasAuthority('ROLE_ADMIN')")
    public String logout(Authentication authentication, HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response,
                authentication);
        return "redirect:/";
    }
}
