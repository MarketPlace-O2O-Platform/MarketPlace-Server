package com.appcenter.marketplace.global.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;

/**
 * 방문자 추적 필터 (Redis 기반)
 * - 하루 순방문자 수 (Unique Visitors) - Redis Set
 * - 전체 페이지뷰 (Page Views) - Redis Counter
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class VisitorTrackingFilter implements Filter {

    private final StringRedisTemplate redisTemplate;

    private static final String DAILY_VISITORS_KEY = "currumi:visitors:daily:";
    private static final String DAILY_PAGEVIEWS_KEY = "currumi:pageviews:daily:";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        if (request instanceof HttpServletRequest httpRequest) {
            String ipAddress = getClientIpAddress(httpRequest);
            String uri = httpRequest.getRequestURI();

            if (!isStaticResource(uri)) {
                String today = LocalDate.now().toString();

                // 1. 전체 페이지뷰 카운트 (Redis 날짜별 카운터)
                String pageviewKey = DAILY_PAGEVIEWS_KEY + today;
                redisTemplate.opsForValue().increment(pageviewKey);
                redisTemplate.expire(pageviewKey, Duration.ofDays(2));

                // 2. Redis 기반 고유 방문자 추적
                String visitorKey = DAILY_VISITORS_KEY + today;
                redisTemplate.opsForSet().add(visitorKey, ipAddress);
                redisTemplate.expire(visitorKey, Duration.ofDays(2));
            }
        }

        chain.doFilter(request, response);
    }

    private String getClientIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) ip = request.getHeader("Proxy-Client-IP");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) ip = request.getHeader("WL-Proxy-Client-IP");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) ip = request.getHeader("HTTP_CLIENT_IP");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) ip = request.getRemoteAddr();
        if (ip != null && ip.contains(",")) ip = ip.split(",")[0].trim();
        return ip;
    }

    private boolean isStaticResource(String uri) {
        return uri.matches(".+\\.(css|js|jpg|jpeg|png|gif|ico|svg|woff|woff2|ttf|eot)$");
    }
}
