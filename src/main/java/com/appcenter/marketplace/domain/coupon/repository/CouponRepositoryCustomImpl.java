package com.appcenter.marketplace.domain.coupon.repository;

import com.appcenter.marketplace.domain.coupon.dto.res.*;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import static com.appcenter.marketplace.domain.coupon.QCoupon.coupon;
import static com.appcenter.marketplace.domain.local.QLocal.local;
import static com.appcenter.marketplace.domain.market.QMarket.market;
import static com.appcenter.marketplace.domain.member_coupon.QMemberCoupon.memberCoupon;
import static com.appcenter.marketplace.domain.metro.QMetro.metro;

@RequiredArgsConstructor
public class CouponRepositoryCustomImpl implements CouponRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    // 사장님) '숨김처리'에 관계없이 발행한 모든 쿠폰을 확인할 수 있습니다.
    @Override
    public List<CouponRes> findCouponsForOwnerByMarketId(Long marketId, Long couponId, Integer size) {

        return jpaQueryFactory.select(new QCouponRes(coupon.id,
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
    public List<CouponRes> findCouponsForMemberByMarketId(Long memberId, Long marketId, Long couponId, Integer size) {

        return jpaQueryFactory.select(new QCouponRes(
                        coupon.id,
                        coupon.name,
                        coupon.description,
                        coupon.deadLine,
                        coupon.stock.gt(0),
                        memberCoupon.id.isNotNull()))
                .from(coupon)
                .leftJoin(memberCoupon).on(coupon.eq(memberCoupon.coupon)
                        .and(memberCoupon.member.id.eq(memberId)))
                .where(coupon.market.id.eq(marketId)
                        .and(ltCouponId(couponId))
                        .and(coupon.isDeleted.eq(false))
                        .and(coupon.isHidden.eq(false)))
                .orderBy(coupon.id.desc())
                .limit(size + 1)
                .fetch();

    }

    // 최신 등록 쿠폰의 매장 페이징 조회
    @Override
    public List<LatestCouponRes> findLatestCouponList(Long memberId, LocalDateTime lastCreatedAt, Long lastCouponId, Integer size) {
        return jpaQueryFactory
                .select(new QLatestCouponRes(
                        coupon.id,
                        coupon.name,
                        market.id,
                        market.name,
                        metro.name.concat(" ").concat(local.name),
                        market.thumbnail,
                        coupon.stock.gt(0),
                        memberCoupon.id.isNotNull(),
                        coupon.createdAt
                ))
                .from(coupon)
                .innerJoin(coupon.market, market)
                .innerJoin(local).on(market.local.eq(local))
                .innerJoin(metro).on(local.metro.eq(metro))
                .leftJoin(memberCoupon).on(coupon.eq(memberCoupon.coupon)
                        .and(memberCoupon.member.id.eq(memberId)))
                .where(loeCreateAtAndLtCouponId(lastCreatedAt,lastCouponId)
                        .and(coupon.isDeleted.eq(false))
                        .and(coupon.isHidden.eq(false))
                        .and(coupon.deadLine.after(LocalDateTime.now())))
                .orderBy(coupon.createdAt.desc(), coupon.id.desc()) // 최신순 정렬
                .limit(size + 1) // 다음 페이지 여부 확인용 1개 추가 조회
                .fetch();
    }

    // 마감 임박 쿠폰 조회
    @Override
    public List<ClosingCouponRes> findClosingCouponList(Integer size) {
        return jpaQueryFactory.select(new QClosingCouponRes(
                        coupon.id,
                        coupon.name,
                        coupon.deadLine,
                        market.id,
                        market.name,
                        market.thumbnail))
                .from(coupon)
                .innerJoin(coupon.market, market)
                .where(coupon.isDeleted.eq(false)
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

    private BooleanBuilder loeCreateAtAndLtCouponId(LocalDateTime createdAt, Long couponId){
        BooleanBuilder builder = new BooleanBuilder();
        if (createdAt != null && couponId!=null) {
            builder.and(coupon.createdAt.loe(createdAt));

            // A or B,둘중 하나의 조건만 만족하면 true
            builder.and(coupon.createdAt.lt(createdAt).or(coupon.id.lt(couponId)));
        }
        return builder;

    }
}
