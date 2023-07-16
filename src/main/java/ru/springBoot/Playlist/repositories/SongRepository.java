package ru.springBoot.Playlist.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.springBoot.Playlist.models.Song;

import java.util.List;

@Repository
public interface SongRepository extends JpaRepository<Song, Integer> {
}
