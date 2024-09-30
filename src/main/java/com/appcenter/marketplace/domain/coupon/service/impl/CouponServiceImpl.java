package com.appcenter.marketplace.domain.coupon.service.impl;

import com.appcenter.marketplace.domain.coupon.Coupon;
import com.appcenter.marketplace.domain.coupon.dto.req.CouponReqDto;
import com.appcenter.marketplace.domain.coupon.dto.res.CouponResDto;
import com.appcenter.marketplace.domain.coupon.CouponRepository;
import com.appcenter.marketplace.domain.coupon.service.CouponService;
import com.appcenter.marketplace.domain.market.Market;
import com.appcenter.marketplace.domain.market.MarketRepository;
import com.appcenter.marketplace.domain.member_coupon.MemberCoupon;
import com.appcenter.marketplace.domain.member_coupon.MemberCouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {

    private final CouponRepository couponRepository;
    private final MarketRepository marketRepository;
    private final MemberCouponRepository memberCouponRepository;

    @Override
    public CouponResDto createCoupon(CouponReqDto couponReqDto, Long marketId) {

        // MarketId로 존재 유무 파악 한 뒤, 진행
        Market market = marketRepository.findById(marketId).orElseThrow(() -> new IllegalArgumentException("Market not found"));

        Coupon coupon =  couponRepository.save(Coupon.builder()
                .name(couponReqDto.getCouponName())
                .description(couponReqDto.getDescription())
                .deadLine(couponReqDto.getDeadLine())
                .stock(couponReqDto.getStock())
                .isHidden(true)
                .isDeleted(false)
                .market(market)
                .build());

        return CouponResDto.toDto(coupon);

    }
}
