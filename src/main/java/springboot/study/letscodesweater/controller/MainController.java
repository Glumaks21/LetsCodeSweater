package springboot.study.letscodesweater.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springboot.study.letscodesweater.domain.Message;
import springboot.study.letscodesweater.domain.User;
import springboot.study.letscodesweater.service.MessageService;

@Controller
@RequestMapping("/main")
@RequiredArgsConstructor
public class MainController {
    private final MessageService messageService;

    @GetMapping
    public String get(
            @RequestParam(required = false) String filter,
            Model model,
            @PageableDefault(size = 15, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<Message> page = messageService.getMessages(pageable, filter);

        model.addAttribute("filter", filter);
        model.addAttribute("message", new Message());
        model.addAttribute("url", "/main");
        model.addAttribute("page", page);
        return "main";
    }

    @PostMapping
    public String add(
            @ModelAttribute @Valid Message message,
            BindingResult bindingResult,
            @RequestParam("file") MultipartFile file,
            @AuthenticationPrincipal User user,
            Model model,
            @PageableDefault(size = 15, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("message", message);
            model.addAttribute("url", "/main");
            model.addAttribute("page", messageService.getMessages(pageable, null));
            return "main";
        }

        message.setAuthor(user);
        messageService.postMessage(message, file);
        return "redirect:/main";
    }

}
