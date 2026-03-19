package com.appcenter.marketplace.global.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * 방문자 통계 조회 서비스
 * Redis에 저장된 방문자 데이터를 조회
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class VisitorStatsService {

    private final StringRedisTemplate redisTemplate;

    private static final String DAILY_VISITORS_KEY = "currumi:visitors:daily:";
    private static final String DAILY_PAGEVIEWS_KEY = "currumi:pageviews:daily:";

    /**
     * 오늘 전체 페이지뷰 수 조회
     */
    public Long getTodayPageViews() {
        return getPageViewsByDate(LocalDate.now());
    }

    /**
     * 특정 날짜의 전체 페이지뷰 수 조회
     */
    public Long getPageViewsByDate(LocalDate date) {
        String value = redisTemplate.opsForValue().get(DAILY_PAGEVIEWS_KEY + date);
        return value != null ? Long.parseLong(value) : 0L;
    }

    /**
     * 오늘 고유 방문자 수 조회
     */
    public Long getTodayUniqueVisitors() {
        String todayKey = DAILY_VISITORS_KEY + LocalDate.now();
        Long count = redisTemplate.opsForSet().size(todayKey);
        return count != null ? count : 0L;
    }

    /**
     * 특정 날짜의 고유 방문자 수 조회
     */
    public Long getUniqueVisitorsByDate(LocalDate date) {
        String key = DAILY_VISITORS_KEY + date;
        Long count = redisTemplate.opsForSet().size(key);
        return count != null ? count : 0L;
    }

    /**
     * 최근 N일간의 고유 방문자 수 조회
     */
    public Map<LocalDate, Long> getRecentUniqueVisitors(int days) {
        Map<LocalDate, Long> stats = new HashMap<>();
        LocalDate today = LocalDate.now();

        for (int i = 0; i < days; i++) {
            LocalDate date = today.minusDays(i);
            Long count = getUniqueVisitorsByDate(date);
            stats.put(date, count);
        }

        return stats;
    }

    /**
     * 어제와 오늘의 방문자 수 비교
     */
    public VisitorComparison compareWithYesterday() {
        Long today = getTodayUniqueVisitors();
        Long yesterday = getUniqueVisitorsByDate(LocalDate.now().minusDays(1));

        long difference = today - yesterday;
        double changeRate = yesterday > 0 ? ((double) difference / yesterday) * 100 : 0;

        return VisitorComparison.builder()
                .today(today)
                .yesterday(yesterday)
                .difference(difference)
                .changeRate(changeRate)
                .build();
    }

    /**
     * 주간 평균 방문자 수 계산
     */
    public Double getWeeklyAverageVisitors() {
        Map<LocalDate, Long> weeklyStats = getRecentUniqueVisitors(7);
        double sum = weeklyStats.values().stream().mapToLong(Long::longValue).sum();
        return sum / 7.0;
    }

    /**
     * 방문자 비교 DTO
     */
    @lombok.Getter
    @lombok.Builder
    public static class VisitorComparison {
        private Long today;
        private Long yesterday;
        private Long difference;
        private Double changeRate;
    }
}
