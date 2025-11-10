package com.appcenter.marketplace.domain.payback.repository;

import com.appcenter.marketplace.domain.coupon.dto.res.QTopClosingCouponRes;
import com.appcenter.marketplace.domain.coupon.dto.res.QTopLatestCouponRes;
import com.appcenter.marketplace.domain.coupon.dto.res.QTopPopularCouponRes;
import com.appcenter.marketplace.domain.coupon.dto.res.TopClosingCouponRes;
import com.appcenter.marketplace.domain.coupon.dto.res.TopLatestCouponRes;
import com.appcenter.marketplace.domain.coupon.dto.res.TopPopularCouponRes;
import com.appcenter.marketplace.domain.member_coupon.CouponType;
import com.appcenter.marketplace.domain.member_payback.QMemberPayback;
import com.appcenter.marketplace.domain.payback.Payback;
import com.appcenter.marketplace.domain.payback.dto.res.PaybackRes;
import com.appcenter.marketplace.domain.payback.dto.res.QPaybackRes;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.appcenter.marketplace.domain.market.QMarket.market;
import static com.appcenter.marketplace.domain.member_payback.QMemberPayback.memberPayback;
import static com.appcenter.marketplace.domain.payback.QPayback.payback;

@RequiredArgsConstructor
public class PaybackRepositoryCustomImpl implements PaybackRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    // 관리자용) 환급쿠폰 조회 리스트
    @Override
    public List<PaybackRes> findCouponsForAdminByMarketId(Long marketId, Long couponId, Integer size) {

        return queryFactory.select(new QPaybackRes(
                        payback.id,
                        payback.name,
                        payback.description,
                        payback.isHidden,
                        Expressions.constant(CouponType.PAYBACK)))
                .from(payback)
                .join(market).on(payback.market.id.eq(market.id))
                .where(ltCouponId(couponId)
                        .and(payback.market.id.eq(marketId))
                        .and(payback.isDeleted.eq(false)))
                .orderBy(payback.id.desc())
                .limit(size+1)
                .fetch();
    }

    // 유저용 ) 환급 쿠폰 조회 리스트
    @Override
    public List<PaybackRes> findCouponsForMembersByMarketId(Long marketId, Long memberId, Long couponId, Integer size) {
        QMemberPayback paybackCoupon = new QMemberPayback("paybackCoupon");

        return queryFactory.selectDistinct(new QPaybackRes(
                        payback.id,
                        payback.name,
                        payback.description,
                        Expressions.asEnum(CouponType.PAYBACK),
                        memberId != null ?
                                paybackCoupon.id.isNotNull():
                                Expressions.FALSE ))
                .from(payback)
                .join(market).on(payback.market.id.eq(market.id))
                .leftJoin(memberPayback).on(payback.eq(memberPayback.payback))
                .leftJoin(paybackCoupon).on(payback.eq(paybackCoupon.payback)
                        .and(memberId != null ? paybackCoupon.member.id.eq(memberId) : null)) // 저장한 쿠폰 확인용
                .where(ltCouponId(couponId)
                        .and(payback.market.id.eq(marketId))
                        .and(payback.isHidden.eq(false))
                        .and(payback.isDeleted.eq(false)))
                .orderBy(payback.id.desc())
                .limit(size+1)
                .fetch();
    }

    private BooleanBuilder ltCouponId(Long couponId){
        BooleanBuilder builder = new BooleanBuilder();
        if( couponId !=  null){
            builder.and(payback.id.lt(couponId));
        }
        return builder;
    }

    // 관리자용 전체 환급 쿠폰 조회
    @Override
    public List<PaybackRes> findPaybackCouponsForAdmin(Long couponId, Long marketId, Integer size) {
        BooleanBuilder whereClause = new BooleanBuilder();
        whereClause.and(payback.isDeleted.eq(false));

        if (couponId != null) {
            whereClause.and(ltCouponId(couponId));
        }

        if (marketId != null) {
            whereClause.and(payback.market.id.eq(marketId));
        }

        return queryFactory.select(new QPaybackRes(
                        payback.id,
                        payback.name,
                        payback.description,
                        payback.isHidden,
                        Expressions.constant(CouponType.PAYBACK),
                        market.id,
                        market.name))
                .from(payback)
                .innerJoin(payback.market, market)
                .where(whereClause)
                .orderBy(payback.id.desc())
                .limit(size + 1)
                .fetch();
    }

    @Override
    public List<Payback> findPaybacksByMarketId(Long marketId) {
        return queryFactory
                .select(payback)
                .from(payback)
                .where(payback.market.id.eq(marketId)
                        .and(payback.isDeleted.eq(false)))
                .fetch();
    }

    // 마감 임박 Payback TOP 조회 (매장 orderNo 순)
    @Override
    public List<TopClosingCouponRes> findTopClosingPaybackList(Integer size) {
        return queryFactory.select(new QTopClosingCouponRes(
                        payback.id,
                        payback.name,
                        Expressions.nullExpression(), // deadline 없음
                        market.id,
                        market.name,
                        market.thumbnail,
                        Expressions.constant(CouponType.PAYBACK)))
                .from(payback)
                .innerJoin(payback.market, market)
                .where(payback.isDeleted.eq(false)
                        .and(payback.isHidden.eq(false)))
                .orderBy(market.orderNo.asc().nullsLast(), payback.id.desc())
                .limit(size)
                .fetch();
    }

    // 최신 등록 Payback TOP 조회 (매장 orderNo 순)
    @Override
    public List<TopLatestCouponRes> findTopLatestPaybackList(Integer size) {
        return queryFactory
                .select(new QTopLatestCouponRes(
                        payback.id,
                        payback.name,
                        market.id,
                        market.name,
                        market.thumbnail,
                        payback.createdAt,
                        Expressions.constant(CouponType.PAYBACK)
                ))
                .from(payback)
                .innerJoin(payback.market, market)
                .where(payback.isDeleted.eq(false)
                        .and(payback.isHidden.eq(false)))
                .orderBy(payback.createdAt.desc(), market.orderNo.asc().nullsLast())
                .limit(size)
                .fetch();
    }

    // 인기 Payback TOP 조회 (매장 orderNo 순)
    @Override
    public List<TopPopularCouponRes> findTopPopularPaybackList(Integer size) {
        return queryFactory
                .select(new QTopPopularCouponRes(
                        payback.id,
                        payback.name,
                        market.id,
                        market.name,
                        market.thumbnail,
                        memberPayback.id.count(),
                        Expressions.constant(CouponType.PAYBACK)))
                .from(payback)
                .innerJoin(payback.market, market)
                .leftJoin(memberPayback).on(payback.eq(memberPayback.payback))
                .where(payback.isDeleted.eq(false)
                        .and(payback.isHidden.eq(false)))
                .groupBy(payback.id,
                        payback.name,
                        market.id,
                        market.name,
                        market.thumbnail,
                        market.orderNo)
                .orderBy(market.orderNo.asc().nullsLast(), payback.id.desc())
                .limit(size)
                .fetch();
    }

}
