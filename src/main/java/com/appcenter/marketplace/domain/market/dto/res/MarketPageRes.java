package com.appcenter.marketplace.domain.market.dto.res;

import lombok.Getter;

import java.util.List;

@Getter
public class MarketPageRes<T> {
    private final List<T> marketResDtos;
    private final boolean hasNext;

    public MarketPageRes(List<T> marketResDtos, boolean hasNext) {
        this.marketResDtos = marketResDtos;
        this.hasNext = hasNext;
    }
}
