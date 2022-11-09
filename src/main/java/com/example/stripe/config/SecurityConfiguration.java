package com.example.stripe.config;

import java.util.Collection;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.example.stripe.security.JwtFilter;
import com.example.stripe.security.JwtProvider;
import com.example.stripe.service.RedisService;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    
    private final JwtProvider jwtProvider;
    private final RedisService redisService;
    private final StripeProperties stripeProperties;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .cors()
            .and()
            .exceptionHandling()
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .addFilterBefore(
                new JwtFilter(jwtProvider, redisService),
                UsernamePasswordAuthenticationFilter.class
            )
            .authorizeRequests()
            .antMatchers("/auth/logout", "/youtube/**", "/user/user-details").authenticated()
            .antMatchers("/*").permitAll();
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration configuration = stripeProperties.getCors();
        if (isNotEmpty(configuration.getAllowedOrigins())) {
            source.registerCorsConfiguration("/**", configuration);
        }
        return new CorsFilter(source);
    }
    
    private boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }
    
    private boolean isEmpty(Collection<?> collection) {
        return org.springframework.util.CollectionUtils.isEmpty(collection);
    }

}