package ru.springBoot.Playlist.services;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.springBoot.Playlist.models.Author;
import ru.springBoot.Playlist.models.Song;
import ru.springBoot.Playlist.repositories.AuthorRepository;

import java.util.*;
import java.util.stream.IntStream;

import static java.lang.Math.min;
import static java.util.stream.Collectors.toMap;

@Service
@Transactional(readOnly = true)
public class AuthorServices {
    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorServices(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Page<Author> findPagination(Integer page, Integer totalPage) {
        return authorRepository.findAll(PageRequest.of(page - 1, totalPage, Sort.by("name")));
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

    public Map<Integer, List<Song>> getMapSongsByAuthorId(int id) {
        Optional<Author> author = authorRepository.findById(id);
        if (author.isPresent()) {
            Hibernate.initialize(author.get().getSongs());
            List<Song> songs = author.get().getSongs();
            songs.sort(Comparator.comparing(Song::getName));

            int pageSize = 20;
            Map<Integer, List<Song>> map = IntStream.iterate(0, i -> i + pageSize)
                    .limit((songs.size() + pageSize - 1) / pageSize)
                    .boxed()
                    .collect(toMap(i -> (i / pageSize)+1,
                            i -> songs.subList(i, min(i + pageSize, songs.size()))));

            return map;
        } else return Collections.emptyMap();
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
