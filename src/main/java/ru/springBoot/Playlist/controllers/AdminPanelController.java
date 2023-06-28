package ru.springBoot.Playlist.controllers;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.springBoot.Playlist.models.Authors;
import ru.springBoot.Playlist.models.Songs;

@Component
@RequestMapping("/adminPanel")
public class AdminPanelController {
    @GetMapping()
    public String adminPanel() {
        return "/adminPage/admin_panel";
    }

    @GetMapping("/new")
    public String newAuthor(@ModelAttribute("author") Authors author, @ModelAttribute("song") Songs song) {
        return "/adminPage/author_add";
    }

    @GetMapping("/authorEdit")
    public String editAuthor() {
        return "/adminPage/author_edit";
    }

    @GetMapping("/listSongs")
    public String listSongs() {
        return "/adminPage/list_author_songs";
    }

    @GetMapping("songEdit")
    public String editSong() {
        return "/adminPage/song_edit";
    }
}
