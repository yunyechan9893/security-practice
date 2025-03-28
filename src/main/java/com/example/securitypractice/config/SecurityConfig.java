package com.example.securitypractice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

    httpSecurity.authorizeHttpRequests(auth ->
        auth.requestMatchers("/login", "/join").permitAll()
            .requestMatchers("/").permitAll()
            .requestMatchers("/my/**").hasAnyRole("USER", "ADMIN")
            .requestMatchers("/admin").hasRole("ADMIN")
            .anyRequest().authenticated()
    );

    httpSecurity.formLogin(auth -> auth.loginPage("/login") // 권한 없는 페이지 접속 시 로그인 페이지로 이동
        .loginProcessingUrl("/loginProc") // 로그인 처리 진행
        .permitAll()
    );

    httpSecurity.csrf(auth -> auth.disable()); // 로그인 시 토큰을 같이 보내줘야하지만, 추후 구현하므로 임시 장애 처리

    return httpSecurity.build();
  }

}
