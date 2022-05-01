package com.example.springsecurityexample.config;

import com.example.springsecurityexample.repositories.OptRepository;
import com.example.springsecurityexample.security.filters.AuthenticationFilter;
import com.example.springsecurityexample.security.filters.JwtFilter;
import com.example.springsecurityexample.security.providers.AccessTokenAuthenticationProvider;
import com.example.springsecurityexample.security.providers.OptAuthenticationProvider;
import com.example.springsecurityexample.security.providers.RefreshTokenAuthenticationProvider;
import com.example.springsecurityexample.security.providers.UsernamePasswordProvider;
import com.example.springsecurityexample.services.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@AllArgsConstructor
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true
)
public class SecConfig extends WebSecurityConfigurerAdapter {

    private OptRepository optRepository;
    private UserDetailsService userDetailsService;
    private TokenService tokenService;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

//        http.httpBasic();
//
//        http.formLogin();

        http
                .addFilterAt(authenticationFilter(), BasicAuthenticationFilter.class)
                .addFilterAfter(jwtFilter() , BasicAuthenticationFilter.class)
        ;

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests().mvcMatchers("/login/**").permitAll();

        http.authorizeRequests().anyRequest().authenticated();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth
                .authenticationProvider(optAuthenticationProvider())
                .authenticationProvider(usernamePasswordProvider())
                .authenticationProvider(refreshTokenAuthenticationProvider())
                .authenticationProvider(accessTokenAuthenticationProvider())
        ;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Bean
    public AuthenticationFilter authenticationFilter() throws Exception {
        return new AuthenticationFilter(authenticationManagerBean() , tokenService);
    }

    @Bean
    public JwtFilter jwtFilter() throws Exception {
        return new JwtFilter(authenticationManagerBean());
    }


    @Bean
    public UsernamePasswordProvider usernamePasswordProvider(){
        return new UsernamePasswordProvider(userDetailsService , passwordEncoder(), optRepository);
    }

    @Bean
    public OptAuthenticationProvider optAuthenticationProvider(){
        return new OptAuthenticationProvider(userDetailsService  , optRepository);
    }

    @Bean
    public RefreshTokenAuthenticationProvider refreshTokenAuthenticationProvider(){
        return new RefreshTokenAuthenticationProvider(tokenService, userDetailsService);
    }

    @Bean
    public AccessTokenAuthenticationProvider accessTokenAuthenticationProvider(){
        return new AccessTokenAuthenticationProvider(tokenService);
    }

}
