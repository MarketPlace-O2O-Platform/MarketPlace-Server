package com.appcenter.marketplace.global.config;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * 비즈니스 메트릭 관리 클래스
 * Prometheus + Grafana를 통해 실시간 비즈니스 활동을 모니터링
 */
@Component
@RequiredArgsConstructor
public class MetricsConfig {

    private final MeterRegistry meterRegistry;

    /**
     * 회원 가입 메트릭 기록
     */
    public void recordMemberSignup() {
        Counter.builder("currumi.member.signup")
                .description("회원 가입 수")
                .tag("type", "user")
                .register(meterRegistry)
                .increment();
    }

    /**
     * 쿠폰 다운로드 메트릭 기록
     * @param couponId 쿠폰 ID
     * @param couponName 쿠폰 이름
     */
    public void recordCouponDownload(Long couponId, String couponName) {
        Counter.builder("currumi.coupon.download")
                .description("쿠폰 다운로드 수")
                .tag("coupon_id", String.valueOf(couponId))
                .tag("coupon_name", couponName)
                .register(meterRegistry)
                .increment();
    }

    /**
     * 환급 쿠폰 다운로드 메트릭 기록
     */
    public void recordPaybackCouponDownload() {
        Counter.builder("currumi.payback.download")
                .description("환급 쿠폰 다운로드 수")
                .tag("type", "payback")
                .register(meterRegistry)
                .increment();
    }

    /**
     * 환급 처리 완료 메트릭 기록
     */
    public void recordPaybackComplete() {
        Counter.builder("currumi.payback.complete")
                .description("환급 처리 완료 수")
                .tag("status", "completed")
                .register(meterRegistry)
                .increment();
    }

    /**
     * 매장 등록 메트릭 기록
     * @param categoryName 카테고리명
     */
    public void recordMarketRegistration(String categoryName) {
        Counter.builder("currumi.market.registration")
                .description("매장 등록 수")
                .tag("category", categoryName)
                .register(meterRegistry)
                .increment();
    }

    /**
     * 쿠폰 사용 메트릭 기록
     * @param couponId 쿠폰 ID
     */
    public void recordCouponUsage(Long couponId) {
        Counter.builder("currumi.coupon.usage")
                .description("쿠폰 사용 수")
                .tag("coupon_id", String.valueOf(couponId))
                .register(meterRegistry)
                .increment();
    }

    /**
     * 찜하기 메트릭 기록
     * @param marketId 매장 ID
     */
    public void recordFavorite(Long marketId) {
        Counter.builder("currumi.favorite.add")
                .description("찜하기 수")
                .tag("market_id", String.valueOf(marketId))
                .register(meterRegistry)
                .increment();
    }

    /**
     * 응원하기 메트릭 기록
     * @param tempMarketId 임시 매장 ID
     */
    public void recordCheer(Long tempMarketId) {
        Counter.builder("currumi.cheer.add")
                .description("응원하기 수")
                .tag("temp_market_id", String.valueOf(tempMarketId))
                .register(meterRegistry)
                .increment();
    }

    /**
     * API 에러 메트릭 기록
     * @param endpoint API 엔드포인트
     * @param errorType 에러 타입
     */
    public void recordApiError(String endpoint, String errorType) {
        Counter.builder("currumi.api.error")
                .description("API 에러 발생 수")
                .tag("endpoint", endpoint)
                .tag("error_type", errorType)
                .register(meterRegistry)
                .increment();
    }

}