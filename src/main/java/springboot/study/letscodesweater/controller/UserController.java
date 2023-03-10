package springboot.study.letscodesweater.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.*;
import springboot.study.letscodesweater.domain.Message;
import springboot.study.letscodesweater.domain.Role;
import springboot.study.letscodesweater.domain.User;
import springboot.study.letscodesweater.service.MessageService;
import springboot.study.letscodesweater.service.UserService;


@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final MessageService messageService;
    private final SmartValidator validator;


    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public String getUserList(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users";
    }

    @GetMapping("/{user}")
    public String getProfile(
            @PathVariable User user,
            @RequestParam(required = false) Message message,
            @AuthenticationPrincipal User authenticated,
            @PageableDefault(size = 15, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable,
            Model model
    ) {
        Assert.notNull(user, "User is not found");

        model.addAttribute("user", user);
        model.addAttribute("subscribersCount",
               user.getSubscribers().size());
        model.addAttribute("subscriptionsCount",
                user.getSubscriptions().size());
        Page<Message> page = messageService.getUserMessages(user, pageable);
        model.addAttribute("page", page);

        if (message != null) {
            model.addAttribute("message", message);
        }
        if (authenticated != null) {
            model.addAttribute("isOwner", user.equals(authenticated));
            model.addAttribute("isSubscriber",
                    user.getSubscribers().contains(authenticated));
        }
        return "profile";
    }


    @GetMapping("/{user}/subscribe")
    public String subscribe(
            @PathVariable User user,
            @AuthenticationPrincipal User authenticated,
            Model model
    ) {
        Assert.notNull(user, "User is not found");
        Assert.notNull(authenticated, "User is not authenticated");

        userService.subscribe(user, authenticated);

        return "redirect:/users/" + user.getUsername();
    }

    @GetMapping("/{user}/unsubscribe")
    public String unsubscribe(
            @PathVariable User user,
            @AuthenticationPrincipal User authenticated
    ) {
        Assert.notNull(user, "User is not found");
        Assert.notNull(authenticated, "User is not authenticated");

        userService.unsubscribe(user, authenticated);

        return "redirect:/users/" + user.getUsername();
    }

    @GetMapping("/{user}/subscriptions")
    public String showSubscriptions(
            @PathVariable User user,
            Model model
    ) {
        Assert.notNull(user, "User is not found");

        model.addAttribute("user", user);
        return "subscriptions";
    }

    @GetMapping("/{user}/subscribers")
    public String showSubscribers(
            @PathVariable User user,
            Model model
    ) {
        Assert.notNull(user, "User is not found");

        model.addAttribute("user", user);
        return "subscribers";
    }

    @GetMapping("/{user}/update")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String getUpdateUser(@PathVariable User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "user_update";
    }

    @PostMapping("/{user}/update")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String updateUser(
            @PathVariable User user,
            @ModelAttribute User modfifiedUser,
            BindingResult bindingResult,
            Model model
    ) {
        user.setRoles(modfifiedUser.getRoles());
        user.setUsername(modfifiedUser.getUsername());
        validator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("user", modfifiedUser);
            model.addAttribute("roles", Role.values());
            return "user_update";
        }
        userService.updateUser(user);
        return "redirect:/users";
    }

    @GetMapping("/{user}/edit")
    public String getProfileEdit(
            @PathVariable User user,
            @AuthenticationPrincipal User authenticated,
            Model model
    ) {
        Assert.notNull(user, "User is not found");
        Assert.isTrue(user.equals(authenticated), "Not owner of the account " + user.getUsername());
        Assert.isTrue(user.equals(authenticated), "Not owner of the account " + user.getUsername());

        model.addAttribute("user", user);
        return "profile_edit";
    }

    @PostMapping("/{user}/edit")
    public String editProfile(
            @RequestParam String email,
            @RequestParam String password,
            @PathVariable User user,
            @AuthenticationPrincipal User authenticated
    ) {
        Assert.notNull(user, "User is not found");
        Assert.notNull(authenticated, "User is not authenticated");
        Assert.isTrue(user.equals(authenticated), "Not owner of the account " + user.getUsername());

        userService.editUser(authenticated, email, password);
        return "redirect:/profile/edit";
    }

    @PostMapping("/{user}/messages/{message}")
    public String editMessage(
            @ModelAttribute Message edited,
            @PathVariable User user,
            @PathVariable Message message,
            @AuthenticationPrincipal User authenticated
    ) {
        Assert.notNull(user, "User is not found");
        Assert.notNull(authenticated, "User is not authenticated");
        Assert.isTrue(user.equals(authenticated), "Not owner of the account " + user.getUsername());

        message.setText(edited.getText());
        message.setTag(edited.getTag());

        messageService.postMessage(message, null);
        return "redirect:/users/" + user.getUsername();
    }
}
