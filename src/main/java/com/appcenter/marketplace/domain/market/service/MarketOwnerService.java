package com.appcenter.marketplace.domain.market.service;

import com.appcenter.marketplace.domain.market.dto.req.MarketCreateReqDto;
import com.appcenter.marketplace.domain.market.dto.req.MarketUpdateReqDto;
import com.appcenter.marketplace.domain.market.dto.res.MarketResDto;

public interface MarketOwnerService {

    MarketResDto createMarket(MarketCreateReqDto marketCreateReqDto);

    MarketResDto updateMarket(MarketUpdateReqDto marketUpdateReqDto, Long marketId);

    MarketResDto getMarket(Long marketId);

    void deleteMarket(Long marketId);

}
