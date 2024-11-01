package com.appcenter.marketplace.domain.market.repository;

import com.appcenter.marketplace.domain.market.dto.res.MarketResDto;

public interface MarketRepositoryCustom {
    public MarketResDto findMarketResDtoById(Long marketId);
}
