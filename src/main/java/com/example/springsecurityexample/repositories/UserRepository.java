package com.example.springsecurityexample.repositories;

import com.example.springsecurityexample.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User , String> {

    Optional<User> findUserByUsername(String username);
}
