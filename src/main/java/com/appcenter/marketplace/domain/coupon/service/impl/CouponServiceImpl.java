package com.appcenter.marketplace.domain.coupon.service.impl;

import com.appcenter.marketplace.domain.coupon.Coupon;
import com.appcenter.marketplace.domain.coupon.dto.req.CouponHiddenReqDto;
import com.appcenter.marketplace.domain.coupon.dto.req.CouponReqDto;
import com.appcenter.marketplace.domain.coupon.dto.req.CouponUpdateReqDto;
import com.appcenter.marketplace.domain.coupon.dto.res.CouponHiddenResDto;
import com.appcenter.marketplace.domain.coupon.dto.res.CouponResDto;
import com.appcenter.marketplace.domain.coupon.CouponRepository;
import com.appcenter.marketplace.domain.coupon.service.CouponService;
import com.appcenter.marketplace.domain.market.Market;
import com.appcenter.marketplace.domain.market.MarketRepository;
import com.appcenter.marketplace.domain.member_coupon.MemberCouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {

    private final CouponRepository couponRepository;
    private final MarketRepository marketRepository;
    private final MemberCouponRepository memberCouponRepository;

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
    public CouponResDto updateCoupon(CouponUpdateReqDto couponUpdateReqDto, Long couponId) {
        Coupon coupon = findCouponById(couponId);
        coupon.update(couponUpdateReqDto);
        return CouponResDto.toDto(coupon);
    }

    @Override
    @Transactional
    public CouponHiddenResDto updateCouponHidden(CouponHiddenReqDto couponHiddenReqDto, Long couponId) {
        Coupon coupon = findCouponById(couponId);
        coupon.updateHidden(couponHiddenReqDto);
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
        return marketRepository.findById(marketId).orElseThrow(IllegalArgumentException::new);
    }

    private Coupon findCouponById(Long couponId) {
        Coupon coupon = couponRepository.findById(couponId).orElseThrow(IllegalArgumentException::new);

        if (!coupon.getIsDeleted())
            return coupon;

        else throw new IllegalArgumentException();
    }
}
