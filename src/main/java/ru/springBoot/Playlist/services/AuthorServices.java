package ru.springBoot.Playlist.services;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.springBoot.Playlist.models.Author;
import ru.springBoot.Playlist.models.Song;
import ru.springBoot.Playlist.repositories.AuthorRepository;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class AuthorServices {
    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorServices(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<Author> findAll(){
        return authorRepository.findAll();
    }

    public List<Author> findAllAuthor(String searchAuthor) {
        List<Author> authors = authorRepository.findByNameStartingWith(searchAuthor.toUpperCase());
        authors.sort(Comparator.comparing(Author::getName));
        return authors;
    }

    public Author findOneAuthor(int id) {
        Optional<Author> author = authorRepository.findById(id);
        return author.orElse(null);
    }

    public List<Song> getSongsByAuthorId(int id) {
        Optional<Author> author = authorRepository.findById(id);
        if (author.isPresent()) {
            Hibernate.initialize(author.get().getSongs());
            List<Song> songs = author.get().getSongs();
            songs.sort(Comparator.comparing(Song::getName));
            return songs;
        } else return Collections.emptyList();
    }

    @Transactional
    public void save(Author author) {
        authorRepository.save(author);
    }

    @Transactional
    public void update(int id, Author updateAuthor) {
        updateAuthor.setId(id);
        authorRepository.save(updateAuthor);
    }

    @Transactional
    public void delete(int id) {
        authorRepository.deleteById(id);
    }
}
