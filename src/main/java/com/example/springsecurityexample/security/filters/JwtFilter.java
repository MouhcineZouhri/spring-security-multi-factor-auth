package com.example.springsecurityexample.security.filters;

import com.example.springsecurityexample.security.authentications.AccessTokenAuthentication;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@AllArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private AuthenticationManager authenticationManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authorization = request.getHeader("Authorization");

        if(authorization !=null && authorization.startsWith("Bearer")){

            String token = authorization.substring("Bearer ".length());

            Authentication authentication = new AccessTokenAuthentication(token);

            Authentication fullyAuthentication = authenticationManager.authenticate(authentication);

            SecurityContextHolder.getContext().setAuthentication(fullyAuthentication);
        }

        filterChain.doFilter(request , response);

    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return request.getServletPath().equals("/login");
    }
}
