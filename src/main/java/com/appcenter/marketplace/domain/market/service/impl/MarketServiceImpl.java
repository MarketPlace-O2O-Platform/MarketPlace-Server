package com.appcenter.marketplace.domain.market.service.impl;

import com.appcenter.marketplace.domain.market.dto.res.MarketDetailsResDto;
import com.appcenter.marketplace.domain.market.dto.res.MarketPageResDto;
import com.appcenter.marketplace.domain.market.dto.res.MarketResDto;
import com.appcenter.marketplace.domain.market.repository.MarketRepository;
import com.appcenter.marketplace.domain.market.service.MarketService;
import com.appcenter.marketplace.global.common.Major;
import com.appcenter.marketplace.global.common.StatusCode;
import com.appcenter.marketplace.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.appcenter.marketplace.global.common.StatusCode.CATEGORY_NOT_EXIST;


@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class MarketServiceImpl implements MarketService {
    private final MarketRepository marketRepository;

    @Override
    public MarketDetailsResDto getMarketDetails(Long marketId) {
        List<MarketDetailsResDto> marketDetailsResDtoList= marketRepository.findMarketDetailResDtoListById(marketId);

        if(marketDetailsResDtoList.isEmpty())
            throw new CustomException(StatusCode.MARKET_NOT_EXIST);

        return marketDetailsResDtoList.get(0);
    }

    @Override
    public MarketPageResDto getMarketPage(Long marketId, Integer size, String major) {
        List<MarketResDto> marketResDtoList;
        if(major==null){
            marketResDtoList= marketRepository.findMarketResDtoList(marketId,size);
        }
        else if(Major.exists(major)){
            marketResDtoList= marketRepository.findMarketResDtoListByCategory(marketId,size,major);
        }
        else throw new CustomException(CATEGORY_NOT_EXIST);

        return checkHasNextPageAndReturnPageDto(marketResDtoList,size);
    }


    private MarketPageResDto checkHasNextPageAndReturnPageDto(List<MarketResDto> marketResDtoList, Integer size){
        boolean hasNext=false;

        // 가져온 갯수가 페이지 사이즈보다 많으면 다음 페이지가 있는 것이고, 사이즈에 맞게 조정한다.
        if(marketResDtoList.size()>size){
            hasNext=true;
            marketResDtoList.remove(size.intValue());
        }

        return new MarketPageResDto(marketResDtoList,hasNext);
    }
}
