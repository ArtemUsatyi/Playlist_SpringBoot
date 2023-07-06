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
import java.sql.SQLOutput;

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

    @GetMapping("search")
    public String searchAuthor(@RequestParam("searchString") String searchAuthor, Model model) {
        model.addAttribute("search", searchAuthor);
        model.addAttribute("authors", authorServices.findAllAuthor(searchAuthor.toUpperCase()));
        return "/adminPage/admin_panel";
    }

    @GetMapping("new")
    public String newAuthor(@ModelAttribute("author") Author author) {
        return "/adminPage/new_author";
    }

    @PostMapping
    public String createAuthor(@ModelAttribute("author") @Valid Author author, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "/adminPage/new_author";
        authorServices.save(author);
        return "redirect:/adminPanel";
    }

    @GetMapping("{id}/dataAuthor")
    public String dataAuthor(@PathVariable("id") int id, Model model) {
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

    @GetMapping("{id}/listSongs")
    public String listSongs(@PathVariable("id") int id, Model model) {
        model.addAttribute("author", authorServices.findOneAuthor(id));
        model.addAttribute("songs", authorServices.getSongsByAuthorId(id));
        return "/adminPage/list_author_songs";
    }

    @GetMapping("{id}/dataSong")
    public String dataS(@PathVariable("id") int id, Model model) {
        model.addAttribute("song", songServices.findOneSong(id));
        return "/adminPage/data_song";
    }
    private Song song;
    @GetMapping("{id}/editSong")
    public String editSong(@PathVariable("id") int id, Model model) {
        model.addAttribute("song", songServices.findOneSong(id));
        return "/adminPage/edit_song";
    }
    @PatchMapping("{id}/patchSong")
    public String updateAuthor(@ModelAttribute("song") @Valid Song song, BindingResult bindingResult,
                               @PathVariable("id") int id) {
//        System.out.println(song.getId());
//        song = songServices.findOneSong(id);
//        System.out.println(song.getId());
//        System.out.println(song.getAuthor().getId());
        if (bindingResult.hasErrors()) return "/adminPage/edit_song";
        songServices.update(id, song);
        return "redirect:/adminPanel/{id}/dataSong";
    }

    @GetMapping("{id}/newSong")
    public String newSong(@PathVariable("id") int id, @ModelAttribute("song") Song song, Model model) {
        model.addAttribute("author", authorServices.findOneAuthor(id));
        return "/adminPage/new_song";
    }
    // исправить POST SONG будем передавать ID а это и будет Автор и при вызове song.getAuthor.setId(id);
    @PostMapping("newSong")
    public String createSong(@ModelAttribute("song") @Valid Song song, BindingResult bindingResult,
                             @ModelAttribute("author") Author author) {
        if (bindingResult.hasErrors()) return "/adminPage/new_song";
        song.getAuthor().setId(author.getId());
        songServices.save(song);
        return "redirect:/adminPage/list_author_songs";
    }
}
