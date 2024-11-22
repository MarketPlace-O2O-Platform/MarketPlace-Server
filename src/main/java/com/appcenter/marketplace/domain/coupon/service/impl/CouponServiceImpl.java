package com.appcenter.marketplace.domain.coupon.service.impl;

import com.appcenter.marketplace.domain.coupon.dto.res.*;
import com.appcenter.marketplace.domain.coupon.repository.CouponRepository;
import com.appcenter.marketplace.domain.coupon.service.CouponService;
import com.appcenter.marketplace.domain.market.Market;
import com.appcenter.marketplace.domain.market.dto.res.MarketPageResDto;
import com.appcenter.marketplace.domain.market.dto.res.MarketResDto;
import com.appcenter.marketplace.domain.market.repository.MarketRepository;
import com.appcenter.marketplace.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.appcenter.marketplace.global.common.StatusCode.MARKET_NOT_EXIST;

@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {

    private final CouponRepository couponRepository;
    private final MarketRepository marketRepository;

    @Override
    @Transactional
    public List<CouponMemberResDto> getCouponList(Long marketId) {
        Market market = findMarketById(marketId);
        List<CouponMemberResDto> couponList = couponRepository.findMemberCouponResDtoByMarketId(marketId);
        return couponList;
    }

    @Override
    public List<CouponLatestTopResDto> getCouponLatestTop(Integer size) {
        return couponRepository.findLatestTopCouponDtoListByMarket(size);
    }

    @Override
    public CouponMarketPageResDto getLatestCouponList(LocalDateTime lastModifiedAt, Long lastCouponId, Integer size) {
        List<CouponMarketResDto> resDtoList = couponRepository.findLatestCouponMarketResDtoListByMarket(lastModifiedAt, lastCouponId,size);

        if(resDtoList.isEmpty())
            throw new CustomException(MARKET_NOT_EXIST);

        return checkHasNextPageAndReturnPageDto(resDtoList,size);
    }

    @Override
    public List<CouponClosingTopResDto> getCouponClosingTop(Integer size) {
        return couponRepository.findClosingTopCouponDtoList(size);
    }

    private CouponMarketPageResDto checkHasNextPageAndReturnPageDto(List<CouponMarketResDto> marketResDtoList, Integer size){
        boolean hasNext=false;

        // 가져온 갯수가 페이지 사이즈보다 많으면 다음 페이지가 있는 것이고, 사이즈에 맞게 조정한다.
        if(marketResDtoList.size()>size){
            hasNext=true;
            marketResDtoList.remove(size.intValue());
        }

        return new CouponMarketPageResDto(marketResDtoList,hasNext);

    }

    private CouponMarketPageResDto checkHasNextPageAndReturnPageDto(List<CouponMarketResDto> marketResDtoList, Integer size){
        boolean hasNext=false;

        // 가져온 갯수가 페이지 사이즈보다 많으면 다음 페이지가 있는 것이고, 사이즈에 맞게 조정한다.
        if(marketResDtoList.size()>size){
            hasNext=true;
            marketResDtoList.remove(size.intValue());
        }

        return new CouponMarketPageResDto(marketResDtoList,hasNext);
    }


    private Market findMarketById(Long marketId) {
        return marketRepository.findById(marketId).orElseThrow(() -> new CustomException(MARKET_NOT_EXIST));

    }

}
