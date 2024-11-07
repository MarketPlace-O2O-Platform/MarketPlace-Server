package com.appcenter.marketplace.domain.market.repository;

import com.appcenter.marketplace.domain.market.dto.res.MarketDetailResDto;
import com.appcenter.marketplace.domain.market.dto.res.MarketResDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface MarketRepositoryCustom {
    public MarketDetailResDto findMarketDetailResDtoById(Long marketId);

    public Slice<MarketResDto> pagingMarketResDtoList(Long marketId, Pageable pageable);
}
