package com.example.springsecurityexample.security.providers;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.springsecurityexample.entities.User;
import com.example.springsecurityexample.exceptions.UserNotFoundException;
import com.example.springsecurityexample.repositories.UserRepository;
import com.example.springsecurityexample.security.authentications.RefreshTokenAuthentication;
import com.example.springsecurityexample.services.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

@AllArgsConstructor
public class RefreshTokenAuthenticationProvider implements AuthenticationProvider {

    private TokenService tokenService;

    private UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {

        String refreshToken = (String) authentication.getCredentials();

        // DecodedJWT decodedJWT = tokenService.verifyToken(refreshToken);

        String username = tokenService.getUsername(refreshToken);

        UserDetails user = userDetailsService.loadUserByUsername(username);

        return new UsernamePasswordAuthenticationToken(
                user.getUsername(),
                null,
                user.getAuthorities()
        );
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return RefreshTokenAuthentication.class.isAssignableFrom(authentication);
    }
}
