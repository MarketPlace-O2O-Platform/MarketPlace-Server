package com.appcenter.marketplace.domain.stats.service;

import com.appcenter.marketplace.domain.member.repository.MemberRepository;
import com.appcenter.marketplace.domain.member_coupon.repository.MemberCouponRepository;
import com.appcenter.marketplace.domain.member_payback.repository.MemberPaybackRepository;
import com.appcenter.marketplace.domain.stats.DailyVisitorStats;
import com.appcenter.marketplace.domain.stats.dto.res.VisitorStatsRes;
import com.appcenter.marketplace.domain.stats.repository.DailyVisitorStatsRepository;
import com.appcenter.marketplace.global.exception.CustomException;
import com.appcenter.marketplace.global.service.VisitorStatsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static com.appcenter.marketplace.global.common.StatusCode.NOT_FOUND_INFO;

/**
 * 관리자 통계 서비스
 */
@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AdminStatsService {

    private final DailyVisitorStatsRepository dailyStatsRepository;
    private final VisitorStatsService visitorStatsService;
    private final MemberRepository memberRepository;
    private final MemberCouponRepository memberCouponRepository;
    private final MemberPaybackRepository memberPaybackRepository;

    /**
     * 오늘 실시간 통계 조회 (Redis + DB)
     */
    public VisitorStatsRes getTodayStats() {
        LocalDate today = LocalDate.now();
        LocalDateTime start = LocalDateTime.of(today, LocalTime.MIN);
        LocalDateTime end = LocalDateTime.of(today, LocalTime.MAX);

        return VisitorStatsRes.builder()
                .date(today)
                .uniqueVisitors(visitorStatsService.getTodayUniqueVisitors())
                .totalPageViews(visitorStatsService.getTodayPageViews())
                .newMemberSignups(memberRepository.countByCreatedAtBetween(start, end))
                .couponDownloads(memberCouponRepository.countByCreatedAtBetween(start, end))
                .couponUsages(memberCouponRepository.countByIsUsedTrueAndModifiedAtBetween(start, end))
                .paybackDownloads(memberPaybackRepository.countByCreatedAtBetween(start, end))
                .build();
    }

    /**
     * 특정 날짜의 통계 조회 (DB)
     */
    public DailyVisitorStats getStatsByDate(LocalDate date) {
        return dailyStatsRepository.findByDate(date)
                .orElseThrow(() -> new CustomException(NOT_FOUND_INFO));
    }

    /**
     * 최근 N일간의 통계 조회
     */
    public List<DailyVisitorStats> getRecentStats(int days) {
        LocalDate endDate = LocalDate.now().minusDays(1);
        LocalDate startDate = endDate.minusDays(days - 1);
        return dailyStatsRepository.findByDateBetween(startDate, endDate);
    }

    /**
     * 날짜 범위로 통계 조회
     */
    public List<DailyVisitorStats> getStatsByDateRange(LocalDate startDate, LocalDate endDate) {
        return dailyStatsRepository.findByDateBetween(startDate, endDate);
    }
}
