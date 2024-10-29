package com.appcenter.marketplace.domain.coupon.service.impl;

import com.appcenter.marketplace.domain.coupon.Coupon;
import com.appcenter.marketplace.domain.coupon.dto.req.CouponReqDto;
import com.appcenter.marketplace.domain.coupon.dto.req.CouponUpdateReqDto;
import com.appcenter.marketplace.domain.coupon.dto.res.CouponHiddenResDto;
import com.appcenter.marketplace.domain.coupon.dto.res.CouponListResDto;
import com.appcenter.marketplace.domain.coupon.dto.res.CouponResDto;
import com.appcenter.marketplace.domain.coupon.CouponRepository;
import com.appcenter.marketplace.domain.coupon.service.CouponOwnerService;
import com.appcenter.marketplace.domain.market.Market;
import com.appcenter.marketplace.domain.market.MarketRepository;
import com.appcenter.marketplace.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.appcenter.marketplace.global.common.StatusCode.*;

@Service
@RequiredArgsConstructor
public class CouponOwnerServiceImpl implements CouponOwnerService {

    private final CouponRepository couponRepository;
    private final MarketRepository marketRepository;

    @Override
    @Transactional
    public CouponResDto createCoupon(CouponReqDto couponReqDto, Long marketId) {

        // MarketId로 존재 유무 파악
        Market market = findMarketById(marketId);

        Coupon coupon = couponRepository.save(couponReqDto.ofCreate(market));

        return CouponResDto.toDto(coupon);
    }

    @Override
    @Transactional
    public CouponResDto getCoupon(Long couponId) {
        return CouponResDto.toDto(findCouponById(couponId));
    }

    @Override
    @Transactional
    public CouponListResDto getCouponList(Long marketId) {
        Market market = findMarketById(marketId);
        List<CouponResDto> couponList = couponRepository.findAllByMarketId(market.getId()).stream().map(CouponResDto::toDto).toList();
        return CouponListResDto.toDto(couponList);
    }

    @Override
    @Transactional
    public CouponResDto updateCoupon(CouponUpdateReqDto couponUpdateReqDto, Long couponId) {
        Coupon coupon = findCouponById(couponId);
        coupon.update(couponUpdateReqDto);
        return CouponResDto.toDto(coupon);
    }

    @Override
    @Transactional
    public CouponHiddenResDto updateCouponHidden(Long couponId) {
        Coupon coupon = findCouponById(couponId);
        coupon.updateHidden();
        return CouponHiddenResDto.toDto(coupon);
    }

    @Override
    @Transactional
    public void deleteCoupon(Long couponId) {
        Coupon coupon = findCouponById(couponId);
        // 소프트 딜리트 적용
        coupon.deleteCoupon();
    }


    private Market findMarketById(Long marketId) {
        return marketRepository.findById(marketId).orElseThrow(() -> new CustomException(MARKET_NOT_EXIST));

    }

    private Coupon findCouponById(Long couponId) {
        Coupon coupon = couponRepository.findById(couponId).orElseThrow(() -> new CustomException(COUPON_NOT_EXIST));

        // 나중에 쿼리로도 적용 예정
        if (!coupon.getIsDeleted())
            return coupon;

        else throw new CustomException(COUPON_IS_DELETED);
    }
}
