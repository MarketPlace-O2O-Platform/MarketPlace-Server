package com.appcenter.marketplace.domain.market.dto.res;

import lombok.Getter;

import java.util.List;

@Getter
public class MarketPageResDto {
    private final List<MarketResDto> marketResDtos;
    private final boolean hasNext;

    public MarketPageResDto(List<MarketResDto> marketResDtos, boolean hasNext) {
        this.marketResDtos = marketResDtos;
        this.hasNext = hasNext;
    }
}
