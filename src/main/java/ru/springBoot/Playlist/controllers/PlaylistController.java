package ru.springBoot.Playlist.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.springBoot.Playlist.services.AuthorServices;
import ru.springBoot.Playlist.services.SongServices;

@Component
@RequestMapping("/index")
public class PlaylistController {

    private final AuthorServices authorServices;
    private final SongServices songServices;

    @Autowired
    public PlaylistController(AuthorServices authorServices, SongServices songServices) {
        this.authorServices = authorServices;
        this.songServices = songServices;
    }

    @GetMapping
    public String homePage() {
        return "index";
    }

    @GetMapping("{id}")
    public String oneAuthor(@PathVariable("id") int id, Model model) {
        model.addAttribute("author", authorServices.findOneAuthor(id));
        model.addAttribute("songs", authorServices.getSongsByAuthorId(id));
        return "index_play";
    }

    @GetMapping("/search")
    public String searchAuthor(@RequestParam("searchString") String searchAuthor, Model model) {
        model.addAttribute("search", searchAuthor);
        model.addAttribute("authors", authorServices.findAllAuthor(searchAuthor.toUpperCase()));
        return "index";
    }

    @GetMapping("about")
    public String aboutPage() {
        return "about";
    }
}
