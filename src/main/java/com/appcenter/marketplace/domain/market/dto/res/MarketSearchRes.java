package com.appcenter.marketplace.domain.market.dto.res;

import lombok.Getter;

import java.math.BigInteger;

@Getter
public class MarketSearchRes {
    private final BigInteger marketId; // native query로 수동 매핑하면 mysql의 BigInteger타입으로 받아야한다.
    private final String name;
    private final String description;
    private final String address;
    private final String thumbnail;
    private final Boolean isNewCoupon;

    public MarketSearchRes(BigInteger marketId, String name, String description, String address, String thumbnail, Boolean isNewCoupon) {
        this.marketId = marketId;
        this.name = name;
        this.description = description;
        this.address = address;
        this.thumbnail = thumbnail;
        this.isNewCoupon = isNewCoupon;
    }
}
