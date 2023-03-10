package springboot.study.letscodesweater.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {
    @GetMapping("/error")
    public String getErrorLoginPage(Model model) {
        model.addAttribute("messageType", "danger");
        model.addAttribute("message", "Login or password is not correct");
        return "login";
    }
}
