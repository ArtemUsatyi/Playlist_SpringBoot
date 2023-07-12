package ru.springBoot.Playlist.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.springBoot.Playlist.models.Username;
import ru.springBoot.Playlist.repositories.UsernameRepository;
import ru.springBoot.Playlist.security.UsernameDetails;

import java.util.Optional;

@Service
public class UsernameDetailsServices implements UserDetailsService {
    private final UsernameRepository usernameRepository;

    @Autowired
    public UsernameDetailsServices(UsernameRepository usernameRepository) {
        this.usernameRepository = usernameRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Username> user = usernameRepository.findByName(username);

        if (user.isEmpty()) throw new UsernameNotFoundException("User not found");

        return new UsernameDetails(user.get());
    }
}
