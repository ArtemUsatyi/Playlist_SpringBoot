package ru.springBoot.Playlist.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.springBoot.Playlist.models.Song;
import ru.springBoot.Playlist.repositories.SongRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SongServices {
    private final SongRepository songRepository;

    @Autowired
    public SongServices(SongRepository songRepository) {
        this.songRepository = songRepository;
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
