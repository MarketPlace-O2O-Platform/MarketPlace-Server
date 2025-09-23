package com.appcenter.marketplace.domain.coupon.repository;

import com.appcenter.marketplace.domain.coupon.dto.res.*;
import com.appcenter.marketplace.domain.member_coupon.CouponType;
import com.appcenter.marketplace.domain.member_coupon.QMemberCoupon;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.Expressions;
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

        return jpaQueryFactory.selectDistinct(new QCouponRes(
                        coupon.id,
                        coupon.name,
                        coupon.description,
                        coupon.deadLine,
                        coupon.stock.gt(0),
                        memberId != null ?
                                memberCoupon.id.isNotNull() :
                                Expressions.FALSE,
                        Expressions.constant(CouponType.GIFT)))
                .from(coupon)
                .leftJoin(memberCoupon).on(coupon.eq(memberCoupon.coupon)
                        .and(memberId != null ? memberCoupon.member.id.eq(memberId) : null))
                .where(coupon.market.id.eq(marketId)
                        .and(ltCouponId(couponId))
                        .and(coupon.isDeleted.eq(false))
                        .and(coupon.isHidden.eq(false)))
                .orderBy(coupon.id.desc())
                .limit(size + 1)
                .fetch();

    }

    // 최신 등록 쿠폰 페이징 조회
    @Override
    public List<CouponRes> findLatestCouponList(Long memberId, LocalDateTime lastCreatedAt, Long lastCouponId, Integer size) {
        return jpaQueryFactory
                .selectDistinct(new QCouponRes(
                        coupon.id,
                        coupon.name,
                        market.id,
                        market.name,
                        metro.name.concat(" ").concat(local.name),
                        market.thumbnail,
                        coupon.stock.gt(0),
                        memberId != null ?
                                memberCoupon.id.isNotNull() :
                                Expressions.FALSE,
                        coupon.createdAt
                ))
                .from(coupon)
                .innerJoin(coupon.market, market)
                .innerJoin(local).on(market.local.eq(local))
                .innerJoin(metro).on(local.metro.eq(metro))
                .leftJoin(memberCoupon).on(coupon.eq(memberCoupon.coupon)
                        .and(memberId != null ? memberCoupon.member.id.eq(memberId) : null))
                    //    .and(memberCoupon.member.id.eq(memberId)))
                .where(loeCreateAtAndLtCouponId(lastCreatedAt,lastCouponId)
                        .and(coupon.isDeleted.eq(false))
                        .and(coupon.isHidden.eq(false))
                        .and(coupon.deadLine.after(LocalDateTime.now())))
                .orderBy(coupon.createdAt.desc(), coupon.id.desc()) // 최신순 정렬
                .limit(size + 1) // 다음 페이지 여부 확인용 1개 추가 조회
                .fetch();
    }

    // 인기 쿠폰 페이징 조회
    @Override
    public List<CouponRes> findPopularCouponList(Long memberId, Long count, Long couponId, Integer size) {
        QMemberCoupon issuedCoupon = new QMemberCoupon("issuedCoupon"); //해당 사용자의 각 쿠폰의 발급 여부 확인을 위한 별칭 생성
        return jpaQueryFactory
                .selectDistinct(new QCouponRes(
                        coupon.id,
                        coupon.name,
                        market.id,
                        market.name,
                        metro.name.concat(" ").concat(local.name),
                        market.thumbnail,
                        coupon.stock.gt(0),
                        memberId != null ?
                                issuedCoupon.id.isNotNull() :
                                Expressions.FALSE,
                        memberCoupon.id.count()))
                .from(coupon)
                .innerJoin(coupon.market, market)
                .innerJoin(local).on(market.local.eq(local))
                .innerJoin(metro).on(local.metro.eq(metro))
                .leftJoin(memberCoupon).on(coupon.eq(memberCoupon.coupon))
                .leftJoin(issuedCoupon).on(coupon.eq(issuedCoupon.coupon)
                        .and(memberId != null ? issuedCoupon.member.id.eq(memberId) : null))
                        // .and(issuedCoupon.member.id.eq(memberId)))
                .where(coupon.isDeleted.eq(false)
                    .and(coupon.isHidden.eq(false))
                    .and(coupon.deadLine.after(LocalDateTime.now())))
                .groupBy(coupon.id,
                        coupon.name,
                        market.id,
                        market.name,
                        metro.name,
                        local.name,
                        market.thumbnail,
                        coupon.stock,
                        issuedCoupon.id)
                .having(loeIssuedCountAndLtCouponId(count,couponId))
                .orderBy(memberCoupon.id.count().desc(), coupon.id.desc()) // 최신순 정렬
                .limit(size + 1) // 다음 페이지 여부 확인용 1개 추가 조회
                .fetch();
    }

    // 마감 임박 쿠폰 TOP 조회
    @Override
    public List<TopClosingCouponRes> findTopClosingCouponList(Integer size) {
        return jpaQueryFactory.select(new QTopClosingCouponRes(
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

    // 최신 등록 쿠폰 TOP 조회
    @Override
    public List<TopLatestCouponRes> findTopLatestCouponList(Integer size) {
        return jpaQueryFactory
                .select(new QTopLatestCouponRes(
                        coupon.id,
                        coupon.name,
                        market.id,
                        market.name,
                        market.thumbnail,
                        coupon.createdAt
                ))
                .from(coupon)
                .innerJoin(coupon.market, market)
                .where(coupon.isDeleted.eq(false)
                        .and(coupon.isHidden.eq(false))
                        .and(coupon.deadLine.after(LocalDateTime.now())))
                .orderBy(coupon.createdAt.desc(), coupon.id.desc()) // 최신순 정렬
                .limit(size)
                .fetch();
    }

    // 인기 쿠폰 TOP 조회
    @Override
    public List<TopPopularCouponRes> findTopPopularCouponList(Integer size) {
         return jpaQueryFactory
                .select(new QTopPopularCouponRes(
                        coupon.id,
                        coupon.name,
                        market.id,
                        market.name,
                        market.thumbnail,
                        memberCoupon.id.count()))
                .from(coupon)
                .innerJoin(coupon.market, market)
                .leftJoin(memberCoupon).on(coupon.eq(memberCoupon.coupon))
                .where(coupon.isDeleted.eq(false)
                        .and(coupon.isHidden.eq(false))
                        .and(coupon.deadLine.after(LocalDateTime.now())))
                .groupBy(coupon.id,
                        coupon.name,
                        market.id,
                        market.name,
                        market.thumbnail)
                .orderBy(memberCoupon.id.count().desc(), coupon.id.desc()) // 최신순 정렬
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

    private BooleanBuilder loeIssuedCountAndLtCouponId(Long count, Long couponId){
        BooleanBuilder builder = new BooleanBuilder();
        if (count != null && couponId!=null) {
            builder.and(memberCoupon.id.count().loe(count));

            // A or B,둘중 하나의 조건만 만족하면 true
            builder.and(memberCoupon.id.count().lt(count).or(coupon.id.lt(couponId)));
        }
        return builder;

    }

    // 관리자용 전체 쿠폰 조회
    @Override
    public List<CouponRes> findCouponsForAdmin(Long couponId, Long marketId, Integer size) {
        BooleanBuilder whereClause = new BooleanBuilder();
        whereClause.and(coupon.isDeleted.eq(false));

        if (couponId != null) {
            whereClause.and(ltCouponId(couponId));
        }

        if (marketId != null) {
            whereClause.and(coupon.market.id.eq(marketId));
        }

        return jpaQueryFactory.select(new QCouponRes(
                        coupon.id,
                        coupon.name,
                        coupon.description,
                        coupon.deadLine,
                        coupon.stock,
                        coupon.isHidden,
                        market.id,
                        market.name))
                .from(coupon)
                .innerJoin(coupon.market, market)
                .innerJoin(market.local, local)
                .innerJoin(local.metro, metro)
                .where(whereClause)
                .orderBy(coupon.id.desc())
                .limit(size + 1)
                .fetch();
    }
}
