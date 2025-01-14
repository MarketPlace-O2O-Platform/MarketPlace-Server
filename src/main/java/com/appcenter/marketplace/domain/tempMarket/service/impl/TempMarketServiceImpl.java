package com.appcenter.marketplace.domain.tempMarket.service.impl;

import com.appcenter.marketplace.domain.tempMarket.dto.res.TempMarketPageRes;
import com.appcenter.marketplace.domain.tempMarket.dto.res.TempMarketRes;
import com.appcenter.marketplace.domain.tempMarket.repository.TempMarketRepository;
import com.appcenter.marketplace.domain.tempMarket.service.TempMarketService;
import com.appcenter.marketplace.global.common.Major;
import com.appcenter.marketplace.global.exception.CustomException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


import static com.appcenter.marketplace.global.common.StatusCode.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TempMarketServiceImpl implements TempMarketService {
    private final TempMarketRepository tempMarketRepository;

    @Override
    public TempMarketPageRes<TempMarketRes> getMarketList(Long memberId, Long marketId, Integer size, String category) {
        List<TempMarketRes> marketResList;
        if(category == null){
            marketResList = tempMarketRepository.findMarketList(memberId, marketId, size);
        } else if(Major.exists(category)){
            marketResList = tempMarketRepository.findMarketListByCategory(memberId, marketId, size, category);
        }
        else throw new CustomException(CATEGORY_NOT_EXIST);
        return checkNextPageAndReturn(marketResList, size);
    }

    @Override
    public TempMarketPageRes<TempMarketRes> getUpcomingNearMarketList(Long memberId, Long marketId, Integer cheerCount, Integer size) {
        List<TempMarketRes> marketResList = tempMarketRepository.findUpcomingMarketList(memberId, marketId, cheerCount, size);

        return checkNextPageAndReturn(marketResList, size);
    }

    private <T> TempMarketPageRes<T> checkNextPageAndReturn(List<T> marketResDtoList, Integer size){
        boolean hasNext = false;

        if(marketResDtoList != null && marketResDtoList.size() > size){
            hasNext = true;
            marketResDtoList.remove(size.intValue());
        }

        return new TempMarketPageRes<>(marketResDtoList, hasNext);
    }
}
