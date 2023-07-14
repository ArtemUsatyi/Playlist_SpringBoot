package ru.springBoot.Playlist.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.springBoot.Playlist.models.Account;
import ru.springBoot.Playlist.repositories.AccountRepository;

@Service
@Transactional(readOnly = true)
public class RegistrationAccountService {
    private final AccountRepository accountRepository;

    @Autowired
    public RegistrationAccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Transactional
    public void register(Account account) {
        accountRepository.save(account);
    }
}
