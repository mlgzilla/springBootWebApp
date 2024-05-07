package task_tracker.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import task_tracker.domain.User;
import task_tracker.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final UserService userService;

    @GetMapping("/")
    public String getHome() {
        return "index";
    }

//    @GetMapping("/singup")
//    public String singUp() {
//        return "singup";
//    }
//
//    @PostMapping("/singup")
//    public String singUp(User user, Model model) {
//        if (userService.userExists(user.getLogin())) {
//            model.addAttribute("error", "Пользователь с логином: " +
//                    user.getLogin() + " уже существует");
//            return "singup";
//        }
//        userService.create(user);
//        return "redirect:/login";
//    }

//    @GetMapping("/login")
//    public String loginPage(@RequestParam(value = "error", required = false) String error, Model model) {
//        if (error != null) {
//            model.addAttribute("errorMessage", "Неправильный логин или пароль");
//        }
//        return "login";
//    }

    @PostMapping("/logout")
    public String logout(Authentication authentication, HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response,
                authentication);
        return "redirect:/";
    }
}
