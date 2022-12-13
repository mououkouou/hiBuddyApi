package com.example.hibuddy.api.configuration;

import com.example.hibuddy.api.configuration.jwt.CustomAuthEntryPoint;
import com.example.hibuddy.api.configuration.jwt.JwtAuthenticationFilter;
import com.example.hibuddy.api.configuration.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final TokenProvider tokenProvider;
    private final CustomAuthEntryPoint customAuthEntryPoint;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/api/users", "/api/users/**").permitAll()
                .antMatchers("/api/auth", "/api/auth/**").permitAll()
                .antMatchers("/api/emails", "/api/emails/**").permitAll()
                .antMatchers("/api/companions", "/api/companions/**").permitAll()
                .antMatchers("/api/promises", "/api/promises/**").permitAll()
                .antMatchers("/api/famous-mountains", "/api/famous-mountains/**").permitAll()
                .antMatchers("/api/mountain-boards", "/api/mountain-boards/**").permitAll()
                .antMatchers("/api/visited-mountains", "/api/visited-mountains/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .httpBasic()
                .disable();

        http.csrf().disable();
        http.cors();

        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.exceptionHandling()
                .authenticationEntryPoint(customAuthEntryPoint);
        http.addFilterBefore(new JwtAuthenticationFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class);
    }
}
