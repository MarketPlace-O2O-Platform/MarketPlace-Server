package com.appcenter.marketplace.domain.coupon.repository;

import com.appcenter.marketplace.domain.coupon.dto.res.CouponMemberResDto;
import static com.appcenter.marketplace.domain.coupon.QCoupon.coupon;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class CouponRepositoryCustomImpl implements CouponRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    // 사장님) '숨김처리'에 관계없이 발행한 모든 쿠폰을 확인할 수 있습니다.
    @Override
    public List<CouponMemberResDto> findAllByMarketId(Long marketId) {

        return jpaQueryFactory.selectFrom(coupon)
                .where(coupon.market.id.eq(marketId)
                        .and(coupon.isDeleted.eq(false)))
                .fetch()
                .stream()
                .map(CouponMemberResDto::toDto).toList();
    }

    // 유저) 발행한 쿠폰 중 '공개처리'가 된 쿠폰들만 확인 가능합니다.
    @Override
    public List<CouponMemberResDto> findCouponsByMarketId(Long marketId) {
        return jpaQueryFactory.selectFrom(coupon)
                .where(coupon.market.id.eq(marketId)
                        .and(coupon.isDeleted.eq(false))
                        .and(coupon.isHidden.eq(false)))
                .fetch()
                .stream()
                .map(CouponMemberResDto::toDto).toList();

    }

}
