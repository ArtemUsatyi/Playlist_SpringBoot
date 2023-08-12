package ru.springBoot.Playlist.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.springBoot.Playlist.models.Account;
import ru.springBoot.Playlist.services.RegistrationAccountService;

import javax.validation.Valid;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private final RegistrationAccountService registrationAccountService;

    @Autowired
    public AuthController(RegistrationAccountService registrationAccountService) {
        this.registrationAccountService = registrationAccountService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "auth/loginAuth";
    }

    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("account") Account account) {
        return "auth/registration";
    }

    @PostMapping("/registration")
    public String performRegistration(@ModelAttribute("account") @Valid Account account, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "auth/registration";
        registrationAccountService.register(account);
        return "redirect:/auth/login";
    }
}
