package com.appcenter.marketplace.domain.tempMarket.dto.res;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class TempMarketRes {

    private final Long marketId;
    private final String marketName;
    private final String thumbnail;
    private final Long cheerCount;

    @QueryProjection
    public TempMarketRes(Long marketId, String marketName, String thumbnail, Long cheerCount) {
        this.marketId = marketId;
        this.marketName = marketName;
        this.thumbnail = thumbnail;
        this.cheerCount = cheerCount;

    }
}
