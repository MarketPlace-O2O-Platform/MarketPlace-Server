package com.appcenter.marketplace.domain.market.service;

import com.appcenter.marketplace.domain.market.dto.res.MarketDetailsResDto;
import com.appcenter.marketplace.domain.market.dto.res.MarketResDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface MarketService {

    MarketDetailsResDto getMarketDetails(Long marketId);

    Slice<MarketResDto> getPagenatedMarketList(Long marketId, Pageable pageable);
}
