package com.appcenter.marketplace.domain.beta.repository;


import com.appcenter.marketplace.domain.beta.dto.res.BetaCouponRes;
import com.appcenter.marketplace.domain.beta.dto.res.QBetaCouponRes;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.appcenter.marketplace.domain.beta.QBetaCoupon.betaCoupon;
import static com.appcenter.marketplace.domain.beta.QBetaMarket.betaMarket;
import static com.appcenter.marketplace.domain.category.QCategory.category;
import static com.appcenter.marketplace.domain.member.QMember.member;

@RequiredArgsConstructor
public class BetaCouponCustomImpl implements BetaCouponCustom{
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<BetaCouponRes> findAllCoupons(Long memberId, Long betaCouponId, Integer size) {
        return jpaQueryFactory
                .select(new QBetaCouponRes(
                        betaCoupon.id,
                        betaMarket.marketName,
                        betaMarket.couponName,
                        betaMarket.couponDetail,
                        betaMarket.image,
                        betaCoupon.isUsed))
                .from(member)
                .leftJoin(betaCoupon).on(member.eq(betaCoupon.member))
                .leftJoin(betaMarket).on(betaCoupon.betaMarket.eq(betaMarket))
                .where(member.id.eq(memberId)
                        .and(ltBetaCouponId(betaCouponId)))
                .orderBy(betaCoupon.id.desc())
                .limit(size + 1)
                .fetch();
    }

    @Override
    public List<BetaCouponRes> findAllCouponsByCategory(Long memberId, Long betaCouponId, String major, Integer size) {
        return jpaQueryFactory
                .select(new QBetaCouponRes(
                        betaCoupon.id,
                        betaMarket.marketName,
                        betaMarket.couponName,
                        betaMarket.couponDetail,
                        betaMarket.image,
                        betaCoupon.isUsed))
                .from(member)
                .leftJoin(betaCoupon).on(member.eq(betaCoupon.member))
                .leftJoin(betaMarket).on(betaCoupon.betaMarket.eq(betaMarket))
                .innerJoin(category).on(betaMarket.category.eq(category))
                .where(member.id.eq(memberId)
                        .and(ltBetaCouponId(betaCouponId))
                        .and(category.major.stringValue().eq(major)))
                .orderBy(betaCoupon.id.desc())
                .limit(size + 1)
                .fetch();
    }

    private BooleanBuilder ltBetaCouponId(Long betaCouponId){
        BooleanBuilder builder = new BooleanBuilder();  // 조건을 담을 BooleanBuilder 생성
        if (betaCouponId != null) {
            builder.and(betaCoupon.id.lt(betaCouponId));  // marketId가 존재하면 lt 조건을 추가
        }
        return builder;

    }
}
