package com.appcenter.marketplace.domain.market.service;

import com.appcenter.marketplace.domain.market.dto.res.MarketResDto;

public interface MarketService {

    MarketResDto getMarket(Long marketId);
}
