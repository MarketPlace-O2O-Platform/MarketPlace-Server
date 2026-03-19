package com.appcenter.marketplace.global.scheduler;

import com.appcenter.marketplace.global.config.VisitorTrackingFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 방문자 통계 스케줄러
 * - 매일 자정에 일일 고유 방문자 카운트 초기화
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class VisitorResetScheduler {

    private final VisitorTrackingFilter visitorTrackingFilter;

    @Scheduled(cron = "${schedule.cron.run-daily}", zone = "Asia/Seoul")
    public void resetDailyVisitors() {
        log.info("VisitorResetScheduler.resetDailyVisitors: 매일 자정 고유 방문자 카운트 초기화");
        try {
            visitorTrackingFilter.resetDailyVisitors();
            log.info("VisitorResetScheduler.resetDailyVisitors: 고유 방문자 카운트 초기화 완료");
        } catch (Exception e) {
            log.error("VisitorResetScheduler.resetDailyVisitors: 에러 발생 - {}", e.getMessage());
        }
    }
}
