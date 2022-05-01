package com.example.springsecurityexample.repositories;

import com.example.springsecurityexample.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role , Long> {

    Optional<Role> findRolesByName(String username);

    Optional<Role> findRoleById(Long id);

}
