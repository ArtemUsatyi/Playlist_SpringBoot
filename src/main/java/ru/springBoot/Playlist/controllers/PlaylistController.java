package ru.springBoot.Playlist.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.springBoot.Playlist.models.Author;
import ru.springBoot.Playlist.models.Song;
import ru.springBoot.Playlist.services.AuthorServices;

import java.util.List;
import java.util.Map;

@Component
@RequestMapping("/index")
public class PlaylistController {

    private final AuthorServices authorServices;

    @Autowired
    public PlaylistController(AuthorServices authorServices) {
        this.authorServices = authorServices;
    }

    @GetMapping
    public String homePage() {
        return "/index";
    }

    @GetMapping("{id}/{pageId}")
    public String oneAuthor(@PathVariable("id") int id, Model model,
                            @PathVariable("pageId") int pageId) {
        model.addAttribute("author", authorServices.findOneAuthor(id));
        Map<Integer, List<Song>> map = authorServices.getMapSongsByAuthorId(id);
        model.addAttribute("currentPage", pageId);
        model.addAttribute("totalPage", map.size());
        model.addAttribute("songs", map.get(pageId));
        return "/index_play";
    }

    @GetMapping("/search")
    public String searchAuthor(@RequestParam("searchString") String searchAuthor, Model model) {
        model.addAttribute("search", searchAuthor);
        model.addAttribute("authors", authorServices.findAllAuthor(searchAuthor.toUpperCase()));
        return "/index";
    }

    @GetMapping("/all/{pageId}")
    public String allAuthor(@PathVariable("pageId") int pageId, Model model) {
        int pageSize = 30;
        Page<Author> page = authorServices.findPagination(pageId, pageSize);
        model.addAttribute("currentPage", pageId);
        model.addAttribute("totalPage", page.getTotalPages());
        model.addAttribute("authors", page.getContent());
        return "/index";
    }
}
