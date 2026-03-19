package com.appcenter.marketplace.global.scheduler;

import com.appcenter.marketplace.domain.member.repository.MemberRepository;
import com.appcenter.marketplace.domain.member_coupon.repository.MemberCouponRepository;
import com.appcenter.marketplace.domain.member_payback.repository.MemberPaybackRepository;
import com.appcenter.marketplace.domain.stats.DailyVisitorStats;
import com.appcenter.marketplace.domain.stats.repository.DailyVisitorStatsRepository;
import com.appcenter.marketplace.global.service.VisitorStatsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * 일일 통계 저장 스케줄러
 * - 매일 자정 5분에 전날 통계를 DB에 저장
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class DailyStatsScheduler {

    private final VisitorStatsService visitorStatsService;
    private final DailyVisitorStatsRepository dailyStatsRepository;
    private final MemberRepository memberRepository;
    private final MemberCouponRepository memberCouponRepository;
    private final MemberPaybackRepository memberPaybackRepository;

    /**
     * 매일 00:05에 전날 통계를 DB에 저장
     */
    @Scheduled(cron = "0 5 0 * * *", zone = "Asia/Seoul")
    public void saveDailyStats() {
        log.info("DailyStatsScheduler.saveDailyStats: 일일 통계 저장 시작");

        try {
            LocalDate yesterday = LocalDate.now().minusDays(1);

            // 이미 저장된 통계가 있으면 스킵
            if (dailyStatsRepository.findByDate(yesterday).isPresent()) {
                log.info("DailyStatsScheduler.saveDailyStats: {} 통계가 이미 존재함", yesterday);
                return;
            }

            LocalDateTime start = LocalDateTime.of(yesterday, LocalTime.MIN);
            LocalDateTime end = LocalDateTime.of(yesterday, LocalTime.MAX);

            // 1. Redis에서 방문자 통계 조회
            Long uniqueVisitors = visitorStatsService.getUniqueVisitorsByDate(yesterday);
            Long totalPageViews = visitorStatsService.getPageViewsByDate(yesterday);

            // 2. DB에서 전날 통계 집계
            long newMemberSignups = memberRepository.countByCreatedAtBetween(start, end);
            long couponDownloads = memberCouponRepository.countByCreatedAtBetween(start, end);
            long couponUsages = memberCouponRepository.countByIsUsedTrueAndModifiedAtBetween(start, end);
            long paybackDownloads = memberPaybackRepository.countByCreatedAtBetween(start, end);

            // 3. DB에 저장
            DailyVisitorStats stats = DailyVisitorStats.builder()
                    .date(yesterday)
                    .uniqueVisitors(uniqueVisitors)
                    .totalPageViews(totalPageViews)
                    .newMemberSignups(newMemberSignups)
                    .couponDownloads(couponDownloads)
                    .couponUsages(couponUsages)
                    .paybackDownloads(paybackDownloads)
                    .build();

            dailyStatsRepository.save(stats);

            log.info("DailyStatsScheduler.saveDailyStats: {} 통계 저장 완료 - 고유 방문자: {}, 신규가입: {}, 쿠폰다운: {}, 쿠폰사용: {}, 환급다운: {}",
                    yesterday, uniqueVisitors, newMemberSignups, couponDownloads, couponUsages, paybackDownloads);

        } catch (Exception e) {
            log.error("DailyStatsScheduler.saveDailyStats: 에러 발생 - {}", e.getMessage(), e);
        }
    }
}
