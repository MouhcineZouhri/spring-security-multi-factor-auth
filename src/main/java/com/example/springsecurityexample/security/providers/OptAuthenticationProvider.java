package com.example.springsecurityexample.security.providers;

import com.example.springsecurityexample.entities.Opt;
import com.example.springsecurityexample.repositories.OptRepository;
import com.example.springsecurityexample.security.authentications.OptAuthentication;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.time.LocalDateTime;
import java.util.Random;


@AllArgsConstructor
public class OptAuthenticationProvider implements AuthenticationProvider {

    private UserDetailsService userDetailsService;

    private OptRepository optRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = (String) authentication.getPrincipal();

        String code = (String) authentication.getCredentials();

        Opt opt = optRepository.
                getOptAfterCheck(username, code , LocalDateTime.now())
                .orElseThrow(() -> new RuntimeException("something wrong"));

        opt.setIsConsume(true);

        optRepository.save(opt);

        UserDetails user = userDetailsService.loadUserByUsername(username);



        return new UsernamePasswordAuthenticationToken(
                user.getUsername()
                , null
                , user.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return OptAuthentication.class.isAssignableFrom(authentication);
    }


}
