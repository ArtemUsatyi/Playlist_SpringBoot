package ru.springBoot.Playlist.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.springBoot.Playlist.models.Author;
import ru.springBoot.Playlist.models.Song;
import ru.springBoot.Playlist.services.AuthorServices;
import ru.springBoot.Playlist.services.SongServices;

import javax.validation.Valid;

@Component
@RequestMapping("/adminPanel")
public class AdminPanelController {

    private final AuthorServices authorServices;
    private final SongServices songServices;

    @Autowired
    public AdminPanelController(AuthorServices authorServices, SongServices songServices) {
        this.authorServices = authorServices;
        this.songServices = songServices;
    }

    @GetMapping()
    public String adminPanel() {
        return "/adminPage/admin_panel";
    }

    @GetMapping("/new")
    public String newAuthor(@ModelAttribute("author") Author author) {
        return "/adminPage/new_author";
    }

    @GetMapping("/search")
    public String searchAuthor(@RequestParam("searchString") String searchAuthor, Model model) {
        model.addAttribute("search", searchAuthor);
        model.addAttribute("authors", authorServices.findAllAuthor(searchAuthor.toUpperCase()));
        return "/adminPage/admin_panel";
    }

    @PostMapping
    public String createAuthor(@ModelAttribute("author") @Valid Author author, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "/adminPage/new_author";
        authorServices.save(author);
        return "redirect:/adminPanel";
    }
    @GetMapping("{id}/dataAuthor")
    public String dataAuthor(@PathVariable("id") int id, Model model){
        model.addAttribute("author", authorServices.findOneAuthor(id));
        return "/adminPage/data_author";
    }

    @GetMapping("{id}/editAuthor")
    public String editAuthor(@PathVariable("id") int id, Model model) {
        model.addAttribute("author", authorServices.findOneAuthor(id));
        return "/adminPage/edit_author";
    }

    @PatchMapping("{id}/patchAuthor")
    public String updateAuthor(@ModelAttribute("author") @Valid Author author, BindingResult bindingResult,
                               @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) return "/adminPage/edit_author";
        authorServices.update(id, author);
        return "redirect:/adminPanel/{id}/dataAuthor";
    }

    @GetMapping("/listSongs")
    public String listSongs(Model model) {
        return "/adminPage/list_author_songs";
    }

    @GetMapping("editSong")
    public String editSong() {
        return "/adminPage/edit_song";
    }

    @GetMapping("newSong")
    public String newSong(@ModelAttribute("song") Song song) {
        return "/adminPage/new_song";
    }
}
