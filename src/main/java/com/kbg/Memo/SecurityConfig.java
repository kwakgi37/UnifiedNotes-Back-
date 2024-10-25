package com.kbg.Memo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
       http
               .csrf(csrf -> csrf.disable())
               .authorizeHttpRequests(authorize -> authorize
                       .requestMatchers("/api/register", "/api/login", "/api/mailSend", "/api/mailCheck", "/api/pages/add", "/api/pages/{username}", "/api/pages/{username}/{id}", "/api/pages/{id}", "/api/pages/upload", "/uploads/**", "/api/pages/videos/upload", "/api/pages/audios/upload", "/videos/**","/audios/**").permitAll()
                       .anyRequest().authenticated()
               )
               .httpBasic(httpBasic -> httpBasic.disable())  // HTTP Basic 인증 비활성화
               .formLogin(formLogin -> formLogin.disable());  // 폼 로그인 비활성화
       return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
