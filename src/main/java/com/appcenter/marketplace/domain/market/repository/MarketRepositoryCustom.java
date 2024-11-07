package com.appcenter.marketplace.domain.market.repository;

import com.appcenter.marketplace.domain.market.dto.res.MarketDetailResDto;

public interface MarketRepositoryCustom {
    public MarketDetailResDto findMarketResDtoById(Long marketId);
}
