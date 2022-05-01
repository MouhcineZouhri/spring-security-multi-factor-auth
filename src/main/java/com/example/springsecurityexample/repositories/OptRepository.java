package com.example.springsecurityexample.repositories;

import com.example.springsecurityexample.entities.Opt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;


public interface OptRepository extends JpaRepository<Opt ,String> {


    @Query("select o from Opt o where o.username = :username and o.code = :code and o.expireDate > :date")
    Optional<Opt> getOptAfterCheck(
          @Param("username") String username ,
          @Param("code")  String code,
          @Param("date") LocalDateTime localDateTime);
}
