package com.appcenter.marketplace.domain.stats;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 일일 방문자 통계 엔티티
 * 매일 자정에 전날 통계를 DB에 저장하여 장기 보관
 */
@Entity
@Table(name = "daily_visitor_stats")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DailyVisitorStats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 통계 날짜
     */
    @Column(nullable = false, unique = true)
    private LocalDate date;

    /**
     * 고유 방문자 수 (IP 기반)
     */
    @Column(nullable = false)
    private Long uniqueVisitors;

    /**
     * 전체 페이지뷰 수
     */
    @Column(nullable = false)
    private Long totalPageViews;

    /**
     * 활성 회원 수 (로그인한 회원)
     */
    private Long activeMembers;

    /**
     * 신규 회원 가입 수
     */
    private Long newMemberSignups;

    /**
     * 쿠폰 다운로드 수
     */
    private Long couponDownloads;

    /**
     * 쿠폰 사용 수
     */
    private Long couponUsages;

    /**
     * 환급 쿠폰 다운로드 수
     */
    private Long paybackDownloads;

    /**
     * 평균 응답 시간 (초)
     */
    private Double avgResponseTime;

    /**
     * 에러 발생 수
     */
    private Long errorCount;

    /**
     * 통계 생성 시각
     */
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
