package com.reservation.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
//                        .requestMatchers("/", "/home", "/register").permitAll() // 모든 사용자 접근 허용
//                        .requestMatchers("/partner/**").hasRole("PARTNER") // 파트너만 접근 가능
//                        .requestMatchers("/customer/**").hasRole("CUSTOMER") // 고객만 접근 가능
//                        .anyRequest().authenticated() // 그 외 모든 요청은 인증 필요
                        .anyRequest().permitAll()
                )
                .formLogin((form) -> form
                        .loginPage("/login") // 커스텀 로그인 페이지
                        .permitAll()
                )
                .logout(LogoutConfigurer::permitAll // 로그아웃 허용
                )
                .csrf(csrf -> csrf.disable());

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        // 테스트용 인메모리 사용자 설정
        UserDetails partner = User.builder()
                .username("partner@example.com")
                .password(passwordEncoder().encode("password"))
                .roles("PARTNER")
                .build();

        UserDetails customer = User.builder()
                .username("customer@example.com")
                .password(passwordEncoder().encode("password"))
                .roles("CUSTOMER")
                .build();

        return new InMemoryUserDetailsManager(partner, customer);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // 비밀번호 암호화
    }
}
