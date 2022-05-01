package com.example.springsecurityexample;

import com.example.springsecurityexample.entities.User;
import com.example.springsecurityexample.repositories.UserRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class SpringSecurityExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityExampleApplication.class, args);
    }


    @Bean
    public ApplicationRunner runner(UserRepository userRepository , PasswordEncoder passwordEncoder){
        return args -> {
            User user = new User();

            user.setUsername("mohsin");
            user.setPassword(passwordEncoder.encode("mohsin123"));

            userRepository.save(user);
        };
    }
}
