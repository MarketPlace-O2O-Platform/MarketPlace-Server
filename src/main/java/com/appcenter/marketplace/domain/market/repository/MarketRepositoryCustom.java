package com.appcenter.marketplace.domain.market.repository;

import com.appcenter.marketplace.domain.market.dto.res.MarketDetailsResDto;
import com.appcenter.marketplace.domain.market.dto.res.MarketResDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface MarketRepositoryCustom {
    public MarketDetailsResDto findMarketDetailResDtoById(Long marketId);

    public Slice<MarketResDto> findPaginatedMarketResDto(Long marketId, Pageable pageable);
}
