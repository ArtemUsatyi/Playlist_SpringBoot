package ru.springBoot.Playlist.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ru.springBoot.Playlist.services.SecurityDetailsServices;

import java.util.Collections;

@Component
public class AuthProviderImpl implements AuthenticationProvider {

    private final SecurityDetailsServices securityDetailsServices;

    @Autowired
    public AuthProviderImpl(SecurityDetailsServices securityDetailsServices) {
        this.securityDetailsServices = securityDetailsServices;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        UserDetails securityUserDetails = securityDetailsServices.loadUserByUsername(username);

        String password = authentication.getCredentials().toString();
        if (!password.equals(securityUserDetails.getPassword()))
            throw new BadCredentialsException("Incorrect password");

        return new UsernamePasswordAuthenticationToken(securityUserDetails, password, Collections.emptyList());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
