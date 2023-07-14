package ru.springBoot.Playlist.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.springBoot.Playlist.models.Account;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    Optional<Account> findByUsername(String username);
}
