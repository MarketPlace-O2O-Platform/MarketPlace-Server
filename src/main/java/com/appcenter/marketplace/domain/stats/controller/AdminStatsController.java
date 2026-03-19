package com.appcenter.marketplace.domain.stats.controller;

import com.appcenter.marketplace.domain.stats.DailyVisitorStats;
import com.appcenter.marketplace.domain.stats.dto.res.VisitorStatsRes;
import com.appcenter.marketplace.domain.stats.service.AdminStatsService;
import com.appcenter.marketplace.global.common.CommonResponse;
import com.appcenter.marketplace.global.service.VisitorStatsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static com.appcenter.marketplace.global.common.StatusCode.STATS_FOUND;

/**
 * 관리자 통계 API
 */
@Tag(name = "[관리자 통계]", description = "[관리자] 방문자 및 활동 통계 조회")
@RestController
@RequestMapping("/api/admins/stats")
@RequiredArgsConstructor
public class AdminStatsController {

    private final VisitorStatsService visitorStatsService;
    private final AdminStatsService adminStatsService;

    @Operation(summary = "오늘 실시간 통계 조회", description = "오늘의 실시간 방문자 및 활동 통계를 조회합니다.")
    @GetMapping("/today")
    public ResponseEntity<CommonResponse<VisitorStatsRes>> getTodayStats() {
        return ResponseEntity
                .ok(CommonResponse.from(STATS_FOUND.getMessage(),
                        adminStatsService.getTodayStats()));
    }

    @Operation(summary = "특정 날짜 통계 조회", description = "특정 날짜의 통계를 조회합니다 (DB에 저장된 과거 데이터).")
    @GetMapping("/date/{date}")
    public ResponseEntity<CommonResponse<DailyVisitorStats>> getStatsByDate(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity
                .ok(CommonResponse.from(STATS_FOUND.getMessage(),
                        adminStatsService.getStatsByDate(date)));
    }

    @Operation(summary = "최근 N일 통계 조회", description = "최근 N일간의 통계를 조회합니다.")
    @GetMapping("/recent/{days}")
    public ResponseEntity<CommonResponse<List<DailyVisitorStats>>> getRecentStats(@PathVariable int days) {
        return ResponseEntity
                .ok(CommonResponse.from(STATS_FOUND.getMessage(),
                        adminStatsService.getRecentStats(days)));
    }

    @Operation(summary = "날짜 범위 통계 조회", description = "시작일부터 종료일까지의 통계를 조회합니다.")
    @GetMapping("/range")
    public ResponseEntity<CommonResponse<List<DailyVisitorStats>>> getStatsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity
                .ok(CommonResponse.from(STATS_FOUND.getMessage(),
                        adminStatsService.getStatsByDateRange(startDate, endDate)));
    }

    @Operation(summary = "어제 대비 오늘 비교", description = "어제와 오늘의 방문자 수를 비교합니다.")
    @GetMapping("/compare/yesterday")
    public ResponseEntity<CommonResponse<VisitorStatsService.VisitorComparison>> compareWithYesterday() {
        return ResponseEntity
                .ok(CommonResponse.from(STATS_FOUND.getMessage(),
                        visitorStatsService.compareWithYesterday()));
    }

    @Operation(summary = "주간 평균 방문자", description = "최근 7일간의 평균 방문자 수를 조회합니다.")
    @GetMapping("/weekly-average")
    public ResponseEntity<CommonResponse<Double>> getWeeklyAverageVisitors() {
        return ResponseEntity
                .ok(CommonResponse.from(STATS_FOUND.getMessage(),
                        visitorStatsService.getWeeklyAverageVisitors()));
    }

    @Operation(summary = "최근 7일 일별 고유 방문자", description = "최근 7일간의 일별 고유 방문자 수를 조회합니다 (Redis 기반).")
    @GetMapping("/recent-visitors/7days")
    public ResponseEntity<CommonResponse<Map<LocalDate, Long>>> getRecent7DaysVisitors() {
        return ResponseEntity
                .ok(CommonResponse.from(STATS_FOUND.getMessage(),
                        visitorStatsService.getRecentUniqueVisitors(7)));
    }
}
