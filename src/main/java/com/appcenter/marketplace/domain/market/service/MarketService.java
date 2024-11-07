package com.appcenter.marketplace.domain.market.service;

import com.appcenter.marketplace.domain.market.dto.res.MarketDetailResDto;

public interface MarketService {

    MarketDetailResDto getMarket(Long marketId);
}
