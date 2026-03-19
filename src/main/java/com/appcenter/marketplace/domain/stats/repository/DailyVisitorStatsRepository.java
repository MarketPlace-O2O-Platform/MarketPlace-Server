package com.appcenter.marketplace.domain.stats.repository;

import com.appcenter.marketplace.domain.stats.DailyVisitorStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * 일일 방문자 통계 리포지토리
 */
@Repository
public interface DailyVisitorStatsRepository extends JpaRepository<DailyVisitorStats, Long> {

    /**
     * 특정 날짜의 통계 조회
     */
    Optional<DailyVisitorStats> findByDate(LocalDate date);

    /**
     * 날짜 범위로 통계 조회
     */
    @Query("SELECT s FROM DailyVisitorStats s WHERE s.date BETWEEN :startDate AND :endDate ORDER BY s.date DESC")
    List<DailyVisitorStats> findByDateBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    /**
     * 최근 N일간의 통계 조회
     */
    @Query("SELECT s FROM DailyVisitorStats s ORDER BY s.date DESC LIMIT :days")
    List<DailyVisitorStats> findRecentStats(@Param("days") int days);

    /**
     * 특정 날짜 이후의 모든 통계 조회
     */
    List<DailyVisitorStats> findByDateAfterOrderByDateDesc(LocalDate date);
}
