package com.appcenter.marketplace.domain.tempMarket.dto.res;

import lombok.Getter;

import java.util.List;

@Getter
public class TempMarketPageRes<T> {
    private final List<T> marketResDtos;
    private final boolean hasNext;

    public TempMarketPageRes(List<T> marketResDtos, boolean hasNext) {
        this.marketResDtos = marketResDtos;
        this.hasNext = hasNext;
    }
}
