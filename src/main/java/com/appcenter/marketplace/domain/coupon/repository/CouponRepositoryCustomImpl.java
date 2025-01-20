package com.appcenter.marketplace.domain.coupon.repository;

import com.appcenter.marketplace.domain.coupon.QCoupon;
import com.appcenter.marketplace.domain.coupon.dto.res.*;
import com.appcenter.marketplace.domain.market.dto.res.QTopClosingCouponRes;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import static com.appcenter.marketplace.domain.coupon.QCoupon.coupon;
import static com.appcenter.marketplace.domain.market.QMarket.market;

@RequiredArgsConstructor
public class CouponRepositoryCustomImpl implements CouponRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    // 사장님) '숨김처리'에 관계없이 발행한 모든 쿠폰을 확인할 수 있습니다.
    @Override
    public List<CouponRes> findCouponsForOwnerByMarketId(Long marketId, Long couponId, Integer size) {

        return jpaQueryFactory.select(new QCouponRes(coupon.id,
                        coupon.market.id,
                        coupon.name,
                        coupon.description,
                        coupon.deadLine,
                        coupon.stock,
                        coupon.isHidden))
                .from(coupon)
                .join(market).on(coupon.market.id.eq(market.id))
                .where(ltCouponId(couponId)
                        .and(coupon.market.id.eq(marketId))
                        .and(coupon.isDeleted.eq(false)))
                .orderBy(coupon.id.desc())
                .limit(size + 1)
                .fetch();
    }

    // 유저) 사장님이 발행한 쿠폰 중 '공개처리'가 된 쿠폰들만 유저는 리스트에서 확인 가능합니다.
    @Override
    public List<CouponMemberRes> findCouponsForMemberByMarketId(Long marketId, Long couponId, Integer size) {

        return jpaQueryFactory.select(new QCouponMemberRes(coupon.id,
                        coupon.name,
                        coupon.description,
                        coupon.deadLine))
                .from(coupon)
                .innerJoin(coupon).on(coupon.market.id.eq(market.id))
                .where(ltCouponId(couponId)
                        .and(coupon.market.id.eq(marketId))
                        .and(coupon.isDeleted.eq(false))
                        .and(coupon.isHidden.eq(false)))
                .orderBy(coupon.id.desc())
                .limit(size + 1)
                .fetch();

    }

    // 마감 임박 쿠폰 TOP 조회
    @Override
    public List<TopClosingCouponRes> findTopClosingCoupons(Integer size) {

        QCoupon subCoupon = new QCoupon("subCoupon");

        // 서브쿼리: 각 market_id 그룹별 가장 가까운 deadLine을 구함
        JPQLQuery<Tuple> subQuery = JPAExpressions
                .select(subCoupon.market.id, subCoupon.deadLine.min())
                .from(subCoupon)
                .innerJoin(subCoupon.market, market)
                .where(subCoupon.isDeleted.eq(false)
                        .and(subCoupon.isHidden.eq(false))
                        .and(subCoupon.stock.gt(0))
                        .and(subCoupon.deadLine.after(LocalDateTime.now())))
                .groupBy(subCoupon.market.id)
                .limit(1);

        return jpaQueryFactory.select(new QTopClosingCouponRes(
                        market.id,
                        coupon.id,
                        market.name,
                        coupon.name,
                        coupon.deadLine,
                        market.thumbnail))
                .from(coupon)
                .innerJoin(coupon.market, market)
                .where(Expressions.list(coupon.market.id, coupon.deadLine).in(subQuery)
                        .and(coupon.isDeleted.eq(false))
                        .and(coupon.isHidden.eq(false))
                        .and(coupon.stock.gt(0))
                        .and(coupon.deadLine.after(LocalDateTime.now())))
                .orderBy(coupon.deadLine.asc(), coupon.id.desc())
                .limit(size)
                .fetch();
    }

    private BooleanBuilder ltCouponId(Long couponId) {
        BooleanBuilder builder = new BooleanBuilder();
        if( couponId !=  null){
            builder.and(coupon.id.lt(couponId));
        }
        return builder;
    }
}
