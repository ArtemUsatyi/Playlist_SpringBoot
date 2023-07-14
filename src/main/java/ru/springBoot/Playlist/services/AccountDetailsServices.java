package ru.springBoot.Playlist.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.springBoot.Playlist.models.Account;
import ru.springBoot.Playlist.repositories.AccountRepository;
import ru.springBoot.Playlist.security.AccountDetails;

import java.util.Optional;

@Service
public class AccountDetailsServices implements UserDetailsService {
    private final AccountRepository accountRepository;

    @Autowired
    public AccountDetailsServices(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> account = accountRepository.findByUsername(username);

        if (account.isEmpty()) throw new UsernameNotFoundException("User not found");

        return new AccountDetails(account.get());
    }
}
