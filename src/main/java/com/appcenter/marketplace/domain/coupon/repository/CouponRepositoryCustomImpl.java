package com.appcenter.marketplace.domain.coupon.repository;

import com.appcenter.marketplace.domain.coupon.dto.res.*;

import static com.appcenter.marketplace.domain.coupon.QCoupon.coupon;
import static com.appcenter.marketplace.domain.market.QMarket.market;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class CouponRepositoryCustomImpl implements CouponRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    // 사장님) '숨김처리'에 관계없이 발행한 모든 쿠폰을 확인할 수 있습니다.
    @Override
    public List<CouponResDto> findOwnerCouponResDtoByMarketId(Long marketId) {

        return jpaQueryFactory.select(new QCouponResDto(coupon.id,
                        coupon.market.id,
                        coupon.name,
                        coupon.description,
                        coupon.deadLine,
                        coupon.stock,
                        coupon.isHidden))
                .from(coupon)
                .join(market).on(coupon.market.id.eq(market.id))
                .where(coupon.market.id.eq(marketId)
                        .and(coupon.isDeleted.eq(false)))
                .fetch();
    }

    // 유저) 사장님이 발행한 쿠폰 중 '공개처리'가 된 쿠폰들만 유저는 리스트에서 확인 가능합니다.
    @Override
    public List<CouponMemberResDto> findMemberCouponResDtoByMarketId(Long marketId) {

        return jpaQueryFactory.select(new QCouponMemberResDto(coupon.id,
                        coupon.name,
                        coupon.description,
                        coupon.deadLine))
                .from(coupon)
                .innerJoin(coupon).on(coupon.market.id.eq(market.id))
                .where(coupon.market.id.eq(marketId)
                        .and(coupon.isDeleted.eq(false))
                        .and(coupon.isHidden.eq(false)))
                .fetch();

    }
}
