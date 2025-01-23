package com.appcenter.marketplace.domain.tempMarket.dto.res;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TempMarketRes {
    private final Long marketId;
    private final String marketName;
    private String marketDescription;
    private final String thumbnail;
    private Integer cheerCount;
    private final Boolean isCheer;

    @QueryProjection
    public TempMarketRes(Long marketId, String marketName, String thumbnail, Integer cheerCount, Boolean isCheer) {
        this.marketId = marketId;
        this.marketName = marketName;
        this.thumbnail = thumbnail;
        this.cheerCount = cheerCount;
        this.isCheer = isCheer;
    }

    // 매장 검색 조회
    public TempMarketRes(Long marketId, String marketName, String marketDescription, String thumbnail, Long isCheer) {
        this.marketId = marketId;
        this.marketName = marketName;
        this.marketDescription = marketDescription;
        this.thumbnail = thumbnail;
        if (isCheer > 0) { // db에서 Boolean값은 존재하지않아 쿼리결과에서 Long 값을 Boolean값으로 변환해줘야한다.
            this.isCheer = true;
        } else {
            this.isCheer = false;
        }
    }
}
