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
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Random;

@AllArgsConstructor
public class UsernamePasswordProvider implements AuthenticationProvider {

    private final   UserDetailsService userDetailsService;

    private final PasswordEncoder passwordEncoder;

    private final OptRepository optRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username = (String) authentication.getPrincipal();

        String password = (String) authentication.getCredentials();

        System.out.println(username);

        UserDetails user = userDetailsService.loadUserByUsername(username);

        // match  password

        if(!passwordEncoder.matches(password , user.getPassword() )){
            throw new RuntimeException("password not macth");
        }

        Opt opt  = generateOpt(username);

        return new OptAuthentication(username , opt.getCode());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }


    private Opt generateOpt(String username){
        String numbers = "0123456789";

        Random random = new Random();

        char[] code  = new char[4];

        for(int i = 0; i<4 ; i++){
            code[i] = numbers.charAt(random.nextInt(numbers.length()));
        }

        Opt opt = new Opt();

        opt.setUsername(username);
        opt.setCode(String.valueOf(code));

        opt.setExpireDate(LocalDateTime.now().plusMinutes(10));

        return  optRepository.save(opt);
    }
}
