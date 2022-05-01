package com.example.springsecurityexample.services.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.springsecurityexample.entities.Role;
import com.example.springsecurityexample.exceptions.RoleNotFoundException;
import com.example.springsecurityexample.repositories.RoleRepository;
import com.example.springsecurityexample.services.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class TokenServiceImpl implements TokenService {

    private RoleRepository roleRepository;


    @Override
    public String createAccessToken(UsernamePasswordAuthenticationToken user) {

        String username = (String) user.getPrincipal();

        String[] roles = user.getAuthorities()
                .stream()
                .map(grantedAuthority -> {
                    String roleName = grantedAuthority.getAuthority();
                    Role role = roleRepository.findRolesByName(roleName)
                            .orElseThrow(() -> new RoleNotFoundException(roleName));
                    return role.getName();
                }).toArray(String[]::new);

        return JWT.create()
                .withSubject(username)
                .withIssuer("http://localhost:8080")
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 10))
                .withArrayClaim("roles" , roles)
                .sign(Algorithm.HMAC256("secret"));
    }

    @Override
    public String createRefreshToken(UsernamePasswordAuthenticationToken user) {
        String username = (String) user.getPrincipal();

        return JWT.create()
                .withSubject(username)
                .withIssuer("http://localhost:8080")
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 30))
                .sign(Algorithm.HMAC256("secret"));
    }

    @Override
    public DecodedJWT verifyToken(String token) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256("secret")).build();

        return verifier.verify(token);

    }

    @Override
    public String getUsername(String token) {
        DecodedJWT decodedJWT = verifyToken(token);

        return decodedJWT.getSubject();
    }

    @Override
    public List<String> getRolesName(String token) {
        DecodedJWT decodedJWT = verifyToken(token);

        List<String> roles = decodedJWT.getClaim("roles").asList(String.class);

        return roles;
    }
}
