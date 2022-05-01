package com.example.springsecurityexample.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Opt {

    @Id
    private String username;

    private String code;

    @Column(name = "expire_date")
    private LocalDateTime expireDate;

    @Column(name = "is_consume")
    private Boolean isConsume = false;

}
