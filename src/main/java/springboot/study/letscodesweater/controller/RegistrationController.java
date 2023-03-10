package springboot.study.letscodesweater.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.*;
import springboot.study.letscodesweater.service.UserService;
import springboot.study.letscodesweater.transfer.domain.dto.RegistrationForm;


@Controller
@RequestMapping
public class RegistrationController {
    private final UserService userService;
    private final SmartValidator validator;

    @Autowired
    public RegistrationController(UserService userService, SmartValidator validator) {
        this.userService = userService;
        this.validator = validator;
    }

    @GetMapping("/registration")
    public String get(Model model) {
        model.addAttribute("user", new RegistrationForm());
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(
            @ModelAttribute("user") RegistrationForm registrationForm,
            BindingResult bindingResult,
            @RequestParam("g-recaptcha-response") String recaptchaResponse
    ) {
        registrationForm.setRecaptchaResponse(recaptchaResponse);
        validator.validate(registrationForm, bindingResult);

        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getAllErrors());
            registrationForm.setPassword(null);
            registrationForm.setPasswordConfirmation(null);
            registrationForm.setRecaptchaResponse(null);
            return "registration";
        }

        userService.registerUser(
                registrationForm.getUsername(),
                registrationForm.getEmail(),
                registrationForm.getPassword()
        );
        return "redirect:/login";
    }

    @GetMapping("/activate/{code}")
    public String activeUser(@PathVariable String code, Model model) {
        boolean isActivated = userService.activateUser(code);

        if (isActivated) {
            model.addAttribute("messageType", "success");
            model.addAttribute("message", "User successfully activated");
        } else {
            model.addAttribute("messageType", "danger");
            model.addAttribute("message", "Activation code is not found");
        }

        return "login";
    }
}
