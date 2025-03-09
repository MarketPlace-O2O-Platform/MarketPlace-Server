package com.appcenter.marketplace.global.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtTokenProvider {
    private final CustomUserDetailsService userDetailsService;

    @Value("${jwt.secret.key}")
    private String secret;

    private SecretKey secretKey;

    @Value("${jwt.token-valid-time}")
    private Long accessTokenValidTime;

    // SecretKey 초기화
    @PostConstruct
    protected void init() {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String createAccessToken(Long memberId, String role) {
        Claims claims = Jwts.claims().setSubject(memberId.toString());
        claims.put("role", role);
        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + accessTokenValidTime))
                .signWith(secretKey)
                .compact();
    }

    //인증 성공후 SecurityContextHolder 에 담을 객체(Authentication) 생성
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getMemberId(token));
        log.info("JwtTokenProvider.getAuthentication:  userDetails 가져옴 - {}", userDetails.getUsername());
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // token 으로 memberId 추출
    public String getMemberId(String token) {
        return parseClaims(token).getSubject();
    }

    // http 헤더로부터 bearer 토큰 분리
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if(bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    //토큰 검증
    public boolean validateToken(String token) {
        try{
            Date expiration = parseClaims(token).getExpiration();
            return !expiration.before(new Date());
        } catch (Exception e){
            log.error("JwtTokenProvider.validateToken: 토큰 유효 체크 예외 발생");
            return false;
        }
    }

    // 토큰 분리 (파싱)
    private Claims parseClaims(String token) {
        try {
            Claims claims = Jwts.parserBuilder().setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            log.info("JwtTokenProvider.parseClaims: 토큰 검증 완료, 파싱 클레임 - {}", claims);
            return claims;
        }catch(ExpiredJwtException e) {
            log.error("JwtTokenProvider.parseClaims: Jwt 만료됨");
        }catch(JwtException ex){
            log.error("JwtTokenProvider.parseClaims: Jwt 토큰 예외 발생 {}", ex.getMessage());
            throw ex;
        }
        return null;
    }
}
