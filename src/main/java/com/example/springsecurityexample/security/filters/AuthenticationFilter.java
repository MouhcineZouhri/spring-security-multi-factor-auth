package com.example.springsecurityexample.security.filters;

import com.example.springsecurityexample.repositories.OptRepository;
import com.example.springsecurityexample.security.authentications.OptAuthentication;
import com.example.springsecurityexample.security.authentications.RefreshTokenAuthentication;
import com.example.springsecurityexample.services.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@AllArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {

    private AuthenticationManager authenticationManager;

    private TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request
            , HttpServletResponse response
            , FilterChain filterChain) throws ServletException, IOException {

        String type = request.getHeader("type") != null ? request.getHeader("type") : "" ;

        String username = request.getHeader("username") !=null
                ? request.getHeader("username") : null ;

        if(username.equals("")) throw new RuntimeException("username error");

        Authentication authentication = null;

        switch (type){

            case "code" :
                String  code = request.getHeader("code");

                authentication = new OptAuthentication(username , code);

                UsernamePasswordAuthenticationToken fullyAuthentication =(UsernamePasswordAuthenticationToken)
                        authenticationManager.authenticate(authentication);

                String accessToken  = tokenService.createAccessToken(fullyAuthentication);

                String sendRefreshToken  = tokenService.createRefreshToken(fullyAuthentication);

                response.setHeader("access_token" , accessToken);

                response.setHeader("refresh_token" , sendRefreshToken);

                break;

            case "refresh_token" :
                String refreshToken  = request.getHeader("token");

                authentication = new RefreshTokenAuthentication(refreshToken);

                UsernamePasswordAuthenticationToken refreshFullTokenAuthentication =
                        (UsernamePasswordAuthenticationToken) authenticationManager.authenticate(authentication);

                String newAccessToken   = tokenService.createAccessToken(refreshFullTokenAuthentication);

                response.setHeader("access_token" , newAccessToken);

                response.setHeader("refresh_token" , refreshToken);


                break;

            default:
                // Default is Username and Password

                String password = request.getHeader("password");

                authentication = new UsernamePasswordAuthenticationToken(username , password);

                Authentication authenticate = authenticationManager.authenticate(authentication);

                String codeOpt = (String) authenticate.getCredentials();

                response.setHeader("code" , codeOpt);

                break;

        }

    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !request.getServletPath().equals("/login");
    }


}


