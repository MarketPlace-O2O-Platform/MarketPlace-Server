package com.appcenter.marketplace.domain.payback.repository;

import com.appcenter.marketplace.domain.coupon.dto.res.*;
import com.appcenter.marketplace.domain.member_coupon.CouponType;
import com.appcenter.marketplace.domain.member_payback.QMemberPayback;
import com.appcenter.marketplace.domain.payback.Payback;
import com.appcenter.marketplace.domain.payback.dto.res.PaybackRes;
import com.appcenter.marketplace.domain.payback.dto.res.QPaybackRes;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import static com.appcenter.marketplace.domain.local.QLocal.local;
import static com.appcenter.marketplace.domain.market.QMarket.market;
import static com.appcenter.marketplace.domain.member_payback.QMemberPayback.memberPayback;
import static com.appcenter.marketplace.domain.metro.QMetro.metro;
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
                        Expressions.asEnum(CouponType.PAYBACK)))
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
                                paybackCoupon.id.isNotNull() :
                                Expressions.FALSE))
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
                        Expressions.asEnum(CouponType.PAYBACK),
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
                        Expressions.asEnum(CouponType.PAYBACK)))
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
                        Expressions.asEnum(CouponType.PAYBACK)
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
                        Expressions.asEnum(CouponType.PAYBACK)))
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

    // 최신 등록 Payback 페이징 조회
    @Override
    public List<CouponRes> findLatestPaybackList(Long memberId, LocalDateTime lastCreatedAt, Long lastPaybackId, Integer size) {
        QMemberPayback savedPayback = new QMemberPayback("savedPayback");

        return queryFactory
                .select(new QCouponRes(
                        payback.id,
                        payback.name,
                        market.id,
                        market.name,
                        metro.name.concat(" ").concat(local.name),
                        market.thumbnail,
                        Expressions.TRUE, // Payback은 항상 available
                        memberId != null ?
                                savedPayback.id.isNotNull() :
                                Expressions.FALSE,
                        payback.createdAt,
                        Expressions.asEnum(CouponType.PAYBACK)
                ))
                .from(payback)
                .innerJoin(payback.market, market)
                .innerJoin(market.local, local)
                .innerJoin(local.metro, metro)
                .leftJoin(savedPayback).on(payback.eq(savedPayback.payback)
                        .and(memberId != null ? savedPayback.member.id.eq(memberId) : null))
                .where(loeCreateAtAndLtPaybackId(lastCreatedAt, lastPaybackId)
                        .and(payback.isDeleted.eq(false))
                        .and(payback.isHidden.eq(false)))
                .orderBy(payback.createdAt.desc(), payback.id.desc())
                .limit(size + 1)
                .fetch();
    }

    // 인기 Payback 페이징 조회 (매장 orderNo 순)
    @Override
    public List<CouponRes> findPopularPaybackList(Long memberId, Integer lastOrderNo, Long lastPaybackId, Integer size) {
        QMemberPayback savedPayback = new QMemberPayback("savedPayback");
        QMemberPayback issuedPayback = new QMemberPayback("issuedPayback");

        return queryFactory
                .select(new QCouponRes(
                        payback.id,
                        payback.name,
                        market.id,
                        market.name,
                        metro.name.concat(" ").concat(local.name),
                        market.thumbnail,
                        Expressions.TRUE, // Payback은 항상 available
                        memberId != null ?
                                savedPayback.id.isNotNull() :
                                Expressions.FALSE,
                        issuedPayback.id.count(),
                        market.orderNo, // orderNo 추가
                        Expressions.asEnum(CouponType.PAYBACK)
                ))
                .from(payback)
                .innerJoin(payback.market, market)
                .innerJoin(market.local, local)
                .innerJoin(local.metro, metro)
                .leftJoin(issuedPayback).on(payback.eq(issuedPayback.payback))
                .leftJoin(savedPayback).on(payback.eq(savedPayback.payback)
                        .and(memberId != null ? savedPayback.member.id.eq(memberId) : null))
                .where(payback.isDeleted.eq(false)
                        .and(payback.isHidden.eq(false))
                        .and(goeOrderNoAndGtPaybackId(lastOrderNo, lastPaybackId)))
                .groupBy(payback.id,
                        payback.name,
                        market.id,
                        market.name,
                        metro.name,
                        local.name,
                        market.thumbnail,
                        market.orderNo,
                        savedPayback.id)
                .orderBy(market.orderNo.asc().nullsLast(), payback.id.desc())
                .limit(size + 1)
                .fetch();
    }

    private BooleanBuilder loeCreateAtAndLtPaybackId(LocalDateTime createdAt, Long paybackId) {
        BooleanBuilder builder = new BooleanBuilder();
        if (createdAt != null && paybackId != null) {
            builder.and(payback.createdAt.loe(createdAt));
            builder.and(payback.createdAt.lt(createdAt).or(payback.id.lt(paybackId)));
        }
        return builder;
    }

    private BooleanBuilder goeOrderNoAndGtPaybackId(Integer orderNo, Long paybackId) {
        BooleanBuilder builder = new BooleanBuilder();
        if (orderNo != null && paybackId != null) {
            // orderNo 기준 커서 페이징: orderNo가 더 크거나, 같으면 id가 더 작아야 함 (id는 DESC)
            builder.and(market.orderNo.gt(orderNo).or(
                    market.orderNo.eq(orderNo).and(payback.id.lt(paybackId))
            ));
        } else if (paybackId != null) {
            // orderNo 없이 paybackId만 있는 경우: 모든 orderNo에서 id < paybackId (첫 요청 시)
            builder.and(payback.id.lt(paybackId));
        }
        return builder;
    }

}
