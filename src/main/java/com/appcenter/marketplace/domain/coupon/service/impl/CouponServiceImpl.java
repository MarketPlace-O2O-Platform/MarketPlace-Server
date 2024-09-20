package com.appcenter.marketplace.domain.coupon.service.impl;

import com.appcenter.marketplace.domain.coupon.domain.Coupon;
import com.appcenter.marketplace.domain.coupon.dto.req.CouponReqDto;
import com.appcenter.marketplace.domain.coupon.repository.CouponRepository;
import com.appcenter.marketplace.domain.coupon.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {

    private final CouponRepository couponRepository;

    @Override
    public Coupon createCoupon(CouponReqDto couponReqDto, Long marketId) {

        // MarketId로 존재 유무 파악 한 뒤, 진행

        Coupon coupon = Coupon.builder()
                .name(couponReqDto.getCouponName())
                .description(couponReqDto.getDescription())
                .deadLine(couponReqDto.getDeadLine())
                .stock(couponReqDto.getStock())
                .isHidden(true)
                .isDeleted(false)
//               .market()
                .build();

        couponRepository.save(coupon);

        return coupon;

    }
}
