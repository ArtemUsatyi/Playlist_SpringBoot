package ru.springBoot.Playlist.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Component
@RequestMapping("/user")
@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
public class UserController {

    @GetMapping()
    public String about(){
        return "/userPage/about";
    }
    @GetMapping("403")
    public String page403(){
        return "/userPage/403forbidden";
    }
}
