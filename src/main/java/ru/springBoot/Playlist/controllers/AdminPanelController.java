package ru.springBoot.Playlist.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.springBoot.Playlist.dto.AuthorDTO;
import ru.springBoot.Playlist.dto.SongDTO;
import ru.springBoot.Playlist.models.Author;
import ru.springBoot.Playlist.models.Song;
import ru.springBoot.Playlist.services.AuthorServices;
import ru.springBoot.Playlist.services.SongServices;
import ru.springBoot.Playlist.util.AuthorValidator;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;


@Controller
@RequestMapping("/adminPanel")
@PreAuthorize("hasRole('ADMIN')")
public class AdminPanelController {

    private final AuthorServices authorServices;
    private final SongServices songServices;
    private final AuthorValidator authorValidator;

    @Value("${upload.path}")
    private String uploadPath;
    private Path path;

    @PostConstruct
    private void postConstruct() {
        path = Paths.get(uploadPath);
    }

    @Autowired
    public AdminPanelController(AuthorServices authorServices, SongServices songServices, AuthorValidator authorValidator) {
        this.authorServices = authorServices;
        this.songServices = songServices;
        this.authorValidator = authorValidator;
    }

    @GetMapping()
    public String adminPanel() {
        return "adminPage/admin_panel";
    }

    @GetMapping("/search")
    public String searchAuthor(@RequestParam("searchString") String searchAuthor, Model model) {
        model.addAttribute("search", searchAuthor);
        model.addAttribute("authors", authorServices.findAllAuthor(searchAuthor.toUpperCase()));
        return "adminPage/admin_panel";
    }

    @GetMapping("/new")
    public String newAuthor(@ModelAttribute("authorDTO") AuthorDTO authorDTO) {
        return "adminPage/new_author";
    }

    @PostMapping
    public String createAuthor(@ModelAttribute("authorDTO") @Valid AuthorDTO authorDTO, BindingResult bindingResult) {
        authorValidator.validate(authorDTO, bindingResult);
        if (bindingResult.hasErrors()) return "adminPage/new_author";
        authorDTO.setName(authorDTO.getName().toUpperCase());
        authorServices.save(convertToAuthor(authorDTO));
        return "redirect:/adminPanel";
    }

    @GetMapping("{id}/dataAuthor")
    public String dataAuthor(@PathVariable("id") int id, Model model) {
        model.addAttribute("author", authorServices.findOneAuthor(id));
        return "adminPage/data_author";
    }

    @GetMapping("{id}/editAuthor")
    public String editAuthor(@PathVariable("id") int id, Model model) {
        model.addAttribute("author", authorServices.findOneAuthor(id));
        return "adminPage/edit_author";
    }

    @PatchMapping("{id}/patchAuthor")
    public String updateAuthor(@ModelAttribute("author") @Valid Author author, BindingResult bindingResult,
                               @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) return "adminPage/edit_author";
        author.setName(author.getName().toUpperCase());
        authorServices.update(id, author);
        return "redirect:/adminPanel/{id}/dataAuthor";
    }

    @DeleteMapping("{id}/deleteAuthor")
    public String deleteAuthor(@PathVariable("id") int id) {
        for (Song song : authorServices.getSongsByAuthorId(id)) {
            try {
                Files.delete(path.resolve(song.getLink()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        authorServices.delete(id);
        return "redirect:/adminPanel";
    }

    @GetMapping("{id}/listSongs")
    public String listSongs(@PathVariable("id") int id, Model model) {
        model.addAttribute("author", authorServices.findOneAuthor(id));
        model.addAttribute("songs", authorServices.getSongsByAuthorId(id));
        return "adminPage/list_author_songs";
    }

    @GetMapping("{id}/dataSong")
    public String dataSong(@PathVariable("id") int id, Model model) {
        model.addAttribute("song", songServices.findOneSong(id));
        return "adminPage/data_song";
    }

    @GetMapping("{id}/editSong")
    public String editSong(@PathVariable("id") int id, Model model) {
        model.addAttribute("song", songServices.findOneSong(id));
        return "adminPage/edit_song";
    }

    @PatchMapping("{id}/patchSong")
    public String updateAuthor(@ModelAttribute("song") @Valid Song song, BindingResult bindingResult,
                               @PathVariable("id") int id, Model model, @RequestParam("file") MultipartFile file) {
        song.setAuthor(songServices.findOneSong(id).getAuthor());
        if (bindingResult.hasErrors()) return "adminPage/edit_song";
        if (file.getSize() > 30000000) {
            model.addAttribute("file", "Максимальный размер файла до 30Мб");
            return "adminPage/new_song";
        }
        if (!file.isEmpty()) {
            try {
                Files.delete(path.resolve(songServices.findOneSong(id).getLink()));
                file.transferTo(new File(uploadPath + "/" + song.getLink()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else if (!song.getLink().equals(songServices.findOneSong(id).getLink())) {
            File renameFile = new File(uploadPath + "/" + songServices.findOneSong(id).getLink());
            renameFile.renameTo(new File(uploadPath + "/" + song.getLink()));
        }
        songServices.update(id, song);
        return "redirect:/adminPanel/{id}/dataSong";
    }

    @GetMapping("{id}/newSong")
    public String newSong(@PathVariable("id") int id, @ModelAttribute("author") Author author, Model model) {
        model.addAttribute("author", authorServices.findOneAuthor(id));
        model.addAttribute("songDTO", new SongDTO());
        return "adminPage/new_song";
    }

    @PostMapping("{id}/newSong")
    public String createSong(@ModelAttribute("songDTO") @Valid SongDTO songDTO, BindingResult bindingResult,
                             @PathVariable("id") int id, Model model, @RequestParam("file") MultipartFile file,
                             @ModelAttribute("author") Author author) {
        if (bindingResult.hasErrors()) return "adminPage/new_song";
        if (file.getSize() > 30000000) {
            model.addAttribute("file", "Максимальный размер файла до 30Мб");
            return "adminPage/new_song";
        }
        if (!file.isEmpty()) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) uploadDir.mkdir();
            UUID uuid = UUID.randomUUID();
            songDTO.setLink(uuid + "_" + file.getOriginalFilename());
            try {
                file.transferTo(new File(uploadPath + "/" + songDTO.getLink()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        songServices.save(convertToPerson(songDTO, id));
        model.addAttribute("author", authorServices.findOneAuthor(id));
        model.addAttribute("songs", authorServices.getSongsByAuthorId(id));
        return "adminPage/list_author_songs";
    }

    @DeleteMapping("{id}/deleteSong")
    public String deleteSong(@PathVariable("id") int id, Model model) {
        Author author = songServices.findOneSong(id).getAuthor();
        try {
            Files.delete(path.resolve(songServices.findOneSong(id).getLink()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        songServices.delete(id);
        model.addAttribute("author", author);
        model.addAttribute("songs", author.getSongs());
        return "adminPage/list_author_songs";
    }
    private Song convertToPerson(SongDTO songDTO, int id){
        Song song = new Song();

        song.setName(songDTO.getName());
        song.setLink(songDTO.getLink());
        song.setYearSong(songDTO.getYearSong());

        song.setAuthor(authorServices.findOneAuthor(id));

        return song;
    }

    private Author convertToAuthor(AuthorDTO authorDTO){
        Author author = new Author();

        author.setName(authorDTO.getName());
        author.setAge(authorDTO.getAge());
        author.setEmail(authorDTO.getEmail());

        return author;
    }
}
