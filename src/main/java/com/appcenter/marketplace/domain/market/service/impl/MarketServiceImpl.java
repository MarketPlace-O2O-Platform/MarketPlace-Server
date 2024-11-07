package com.appcenter.marketplace.domain.market.service.impl;

import com.appcenter.marketplace.domain.market.dto.res.MarketDetailsResDto;
import com.appcenter.marketplace.domain.market.dto.res.MarketResDto;
import com.appcenter.marketplace.domain.market.repository.MarketRepository;
import com.appcenter.marketplace.domain.market.service.MarketService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class MarketServiceImpl implements MarketService {
    private final MarketRepository marketRepository;

    @Override
    public MarketDetailsResDto getMarketDetails(Long marketId) {
        return marketRepository.findMarketDetailResDtoById(marketId);
    }

    @Override
    public Slice<MarketResDto> getPagenatedMarketList(Long marketId, Pageable pageable) {
        return marketRepository.findPaginatedMarketResDto(marketId,pageable);
    }
}
