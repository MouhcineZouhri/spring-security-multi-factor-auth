package com.example.springsecurityexample.services;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.List;

public interface TokenService {
    String createAccessToken(UsernamePasswordAuthenticationToken user);

    String createRefreshToken(UsernamePasswordAuthenticationToken user);

    DecodedJWT verifyToken(String token);
    String getUsername(String token);
    List<String> getRolesName(String token);
}
