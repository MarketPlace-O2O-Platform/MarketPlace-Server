package com.appcenter.marketplace.global.config;

import com.appcenter.marketplace.global.exception.JwtExceptionHandlerFilter;
import com.appcenter.marketplace.global.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@EnableWebSecurity
@EnableMethodSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Value("${security.permit-urls.public}")
    private String permitUrls;

    @Value("${security.permit-urls.anonymous}")
    private String anonymousPermitUrls;

    // AuthenticationManager Bean 등록
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // 모든 permit URLs를 하나의 배열로 합치기
        String[] allPermitUrls = getAllPermitUrls();

        http    // token 사용 -> csrf 필요 없음
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configure(http))
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션 사용 안함
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(allPermitUrls).permitAll()
                        .anyRequest().authenticated())
                .anonymous(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JwtExceptionHandlerFilter(), JwtAuthenticationFilter.class);

        return http.build();
    }

    private String[] getAllPermitUrls() {
        List<String> allUrls = new ArrayList<>();

        // 기본 permit-urls 추가
        if (permitUrls != null && !permitUrls.trim().isEmpty()) {
            allUrls.addAll(Arrays.asList(permitUrls.split(",")));
        }

        // 쿠폰 관련 permit-urls 추가
        if (anonymousPermitUrls != null && !anonymousPermitUrls.trim().isEmpty()) {
            allUrls.addAll(Arrays.asList(anonymousPermitUrls.split(",")));
        }

        // 공백 제거 및 배열로 변환
        return allUrls.stream()
                .map(String::trim)
                .filter(url -> !url.isEmpty())
                .toArray(String[]::new);
    }
}
