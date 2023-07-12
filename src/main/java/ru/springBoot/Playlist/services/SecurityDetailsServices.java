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
public class SecurityDetailsServices implements UserDetailsService {
    private final UsernameRepository securityUserRepository;

    @Autowired
    public SecurityDetailsServices(UsernameRepository securityUserRepository) {
        this.securityUserRepository = securityUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Username> user = securityUserRepository.findByName(username);

        if (user.isEmpty()) throw new UsernameNotFoundException("User not found");

        return new UsernameDetails(user.get());
    }
}
