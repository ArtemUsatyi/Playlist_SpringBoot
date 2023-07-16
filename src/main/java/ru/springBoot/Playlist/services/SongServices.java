package ru.springBoot.Playlist.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.springBoot.Playlist.models.Song;
import ru.springBoot.Playlist.repositories.SongRepository;

@Service
@Transactional(readOnly = true)
public class SongServices {
    private final SongRepository songRepository;

    @Autowired
    public SongServices(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    public Song findOneSong(int id){
        songRepository.findAll(Sort.by("name"));
        return songRepository.findById(id).orElse(null);
    }
    @Transactional
    public void save(Song song){
        songRepository.save(song);
    }
    @Transactional
    public void update(int id, Song update){
        update.setId(id);
        songRepository.save(update);
    }
    @Transactional
    public void delete(int id){
        songRepository.deleteById(id);
    }
}
