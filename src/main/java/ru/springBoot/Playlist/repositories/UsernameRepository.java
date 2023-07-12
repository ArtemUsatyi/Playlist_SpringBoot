package ru.springBoot.Playlist.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.springBoot.Playlist.models.Username;

import java.util.Optional;

public interface UsernameRepository extends JpaRepository<Username, Integer> {
    Optional<Username> findByName(String username);
}
