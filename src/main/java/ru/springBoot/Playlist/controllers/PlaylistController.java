package ru.springBoot.Playlist.controllers;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Component
@RequestMapping("/index")
public class PlaylistController {

    @GetMapping
    public String homePage() {
        return "index";
    }

    @GetMapping("login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("registration")
    public String registrationPage() {
        return "registration";
    }

    @GetMapping("about")
    public String aboutPage() {
        return "about";
    }
}
