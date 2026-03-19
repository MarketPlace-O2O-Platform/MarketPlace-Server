package com.appcenter.marketplace.domain.member_payback.dto.res;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class TopMarketPaybackRes {
    private final Long marketId;
    private final String marketName;
    private final Long paybackCount;

    @QueryProjection
    public TopMarketPaybackRes(Long marketId, String marketName, Long paybackCount) {
        this.marketId = marketId;
        this.marketName = marketName;
        this.paybackCount = paybackCount;
    }
}