package com.appcenter.marketplace.domain.market.dto.res;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class MarketResDto {
    private final Long marketId;
    private final String name;
    private final String description;
    private final Long couponId;
    private final String couponName;
    private final String address;
    private final String thumbnail;

    @QueryProjection
    public MarketResDto(Long marketId, String name, String description, Long couponId, String couponName, String address, String thumbnail) {
        this.marketId = marketId;
        this.name = name;
        this.description = description;
        this.couponId = couponId;
        this.couponName = couponName;
        this.address = address;
        this.thumbnail = thumbnail;
    }
}
