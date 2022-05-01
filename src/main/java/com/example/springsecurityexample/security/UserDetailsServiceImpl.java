package com.example.springsecurityexample.security;

import com.example.springsecurityexample.entities.User;
import com.example.springsecurityexample.exceptions.UserNotFoundException;
import com.example.springsecurityexample.repositories.UserRepository;
import com.example.springsecurityexample.security.model.SecurityUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findById(username)
                .orElseThrow(() -> new UserNotFoundException(username));

        return new SecurityUser(user);
    }
}
