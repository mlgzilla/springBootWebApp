package task_tracker.controller;

import com.fasterxml.jackson.core.JsonFactoryBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import task_tracker.domain.ContactInfo;
import task_tracker.domain.User;
import task_tracker.dto.TaskDto;
import task_tracker.dto.UserDto;
import task_tracker.repository.ContactInfoRepository;
import task_tracker.service.ContactInfoService;
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
    private final ContactInfoRepository contactInfoRepository;

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
            }
        }
        return "index";
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
        Result<User> result = userService.create(user);
        ContactInfo contactInfo = new ContactInfo();
        contactInfo.setUser(result.getObject());
        ContactInfo savedContactInfo = contactInfoRepository.saveAndFlush(contactInfo);
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
