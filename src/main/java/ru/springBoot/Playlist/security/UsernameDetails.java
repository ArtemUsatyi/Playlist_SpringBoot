package ru.springBoot.Playlist.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.springBoot.Playlist.models.Username;

import java.util.Collection;

public class UsernameDetails implements UserDetails {
    private final Username username;

    public UsernameDetails(Username securityUser) {
        this.username = securityUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.username.getPassword();
    }

    @Override
    public String getUsername() {
        return this.username.getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    // Нужно чтобы получать данных аутентифицированного пользователя
    public Username getUser() {
        return this.username;
    }
}
