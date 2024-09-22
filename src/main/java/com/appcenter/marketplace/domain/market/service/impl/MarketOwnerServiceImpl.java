package com.appcenter.marketplace.domain.market.service.impl;

import com.appcenter.marketplace.domain.category.Category;
import com.appcenter.marketplace.domain.category.CategoryRepository;
import com.appcenter.marketplace.domain.market.Market;
import com.appcenter.marketplace.domain.market.MarketRepository;
import com.appcenter.marketplace.domain.market.dto.req.MarketCreateReqDto;
import com.appcenter.marketplace.domain.market.dto.req.MarketUpdateReqDto;
import com.appcenter.marketplace.domain.market.dto.res.MarketResDto;
import com.appcenter.marketplace.domain.market.service.MarketOwnerService;
import com.appcenter.marketplace.global.common.Major;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class MarketOwnerServiceImpl implements MarketOwnerService {
    private final MarketRepository marketRepository;
    private final CategoryRepository categoryRepository;


    @Override
    @Transactional
    public MarketResDto createMarket(MarketCreateReqDto marketCreateReqDto) {
        Category category=findCategoryByMajor(marketCreateReqDto.getMajor());
        Market market=marketRepository.save(marketCreateReqDto.toEntity(category));
        return MarketResDto.from(market);
    }

    @Override
    @Transactional
    public MarketResDto updateMarket(MarketUpdateReqDto marketUpdateReqDto, Long marketId) {
        Market market=findMarketByMarketId(marketId);
        Category category=findCategoryByMajor(marketUpdateReqDto.getMajor());
        market.updateMarketInfo(marketUpdateReqDto,category);
        return MarketResDto.from(market);
    }

    @Override
    public MarketResDto getMarket(Long marketId) {
        Market market=findMarketByMarketId(marketId);
        return MarketResDto.from(market);
    }

    @Override
    @Transactional
    public void deleteMarket(Long marketId) {
        marketRepository.deleteById(marketId);
    }

    // 카테고리 조회
    private Category findCategoryByMajor(String major){
        // 카테고리 대분류명 존재 확인
        if(Major.exists(major)) {
            return categoryRepository.findByMajor(Major.valueOf(major))
                    .orElseThrow(IllegalArgumentException::new);
        }
        else throw new IllegalArgumentException();
    }

    // 마켓 조회
    private Market findMarketByMarketId(Long marketId){
        return marketRepository.findById(marketId)
                .orElseThrow(IllegalArgumentException::new);
    }
}
