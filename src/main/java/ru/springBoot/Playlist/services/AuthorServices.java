package ru.springBoot.Playlist.services;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.springBoot.Playlist.models.Author;
import ru.springBoot.Playlist.models.Song;
import ru.springBoot.Playlist.repositories.AuthorRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class AuthorServices {
    private final AuthorRepository authorRepository;
    private final SongServices songServices;

    @Autowired
    public AuthorServices(AuthorRepository authorRepository, SongServices songServices) {
        this.authorRepository = authorRepository;
        this.songServices = songServices;
    }

    public List<Author> findAllAuthor(String searchAuthor) {
        return authorRepository.findByNameStartingWith(searchAuthor);
    }

    public Author findOneAuthor(int id){
        Optional<Author> author = authorRepository.findById(id);
        return author.orElse(null);
    }

    public List<Song> getSongsByAuthorId(int id) {
        Optional<Author> author = authorRepository.findById(id);
        if (author.isPresent()) {
            Hibernate.initialize(author.get().getSongs());
            return author.get().getSongs();
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
