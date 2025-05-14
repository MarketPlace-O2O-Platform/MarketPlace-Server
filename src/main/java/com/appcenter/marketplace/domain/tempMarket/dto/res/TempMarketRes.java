package com.appcenter.marketplace.domain.tempMarket.dto.res;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TempMarketRes {
    private final Long marketId;
    private final String marketName;
    private final String thumbnail;
    private Integer cheerCount;
    private final Boolean isCheer;
    private Integer dueDate;

    @QueryProjection
    public TempMarketRes(Long marketId, String marketName, String thumbnail, Integer cheerCount, Boolean isCheer, LocalDateTime createdAt) {
        this.marketId = marketId;
        this.marketName = marketName;
        this.thumbnail = thumbnail;
        this.cheerCount = cheerCount;
        this.isCheer = isCheer;
        this.dueDate = this.getDueDate(createdAt);
    }

    public Integer getDueDate(LocalDateTime createdAt) {
        if ( createdAt == null ) {
            return null;
        }
        LocalDate dueDate = createdAt.toLocalDate().plusDays(21);
        long diff = ChronoUnit.DAYS.between(LocalDate.now(), dueDate);

        return (int) (diff < 0 ? 0 : diff); //21일 지나면 0일로 통일화
    }

    // 매장 검색 조회
    public TempMarketRes(Long marketId, String marketName, String thumbnail, Long isCheer) {
        this.marketId = marketId;
        this.marketName = marketName;
        this.thumbnail = thumbnail;
        if (isCheer > 0) { // db에서 Boolean값은 존재하지않아 쿼리결과에서 Long 값을 Boolean값으로 변환해줘야한다.
            this.isCheer = true;
        } else {
            this.isCheer = false;
        }
    }
}
