package com.appcenter.marketplace.domain.coupon.repository;

import com.appcenter.marketplace.domain.coupon.QCoupon;
import com.appcenter.marketplace.domain.coupon.dto.res.*;

import static com.appcenter.marketplace.domain.coupon.QCoupon.coupon;
import static com.appcenter.marketplace.domain.market.QMarket.market;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ISourceContext;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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

    @Override
    public List<CouponLatestTopResDto> findLatestTopCouponDtoListByMarket(Integer size) {

        BooleanBuilder whereClause = booleanBuilderSubQuery();

        return  jpaQueryFactory
                .select(new QCouponLatestTopResDto(
                        market.id,
                        coupon.id,
                        market.name,
                        coupon.name,
                        market.thumbnail
                ))
                .from(coupon)
                .innerJoin(coupon.market, market)
                .where(whereClause
                        .and(coupon.isDeleted.eq(false))
                        .and(coupon.isHidden.eq(false))
                        .and(coupon.deadLine.after(LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS))))
                .orderBy(coupon.modifiedAt.desc()) // 최신순 정렬
                .limit(size)
                .fetch();
    }

    @Override
    public List<CouponMarketResDto> findLatestCouponMarketResDtoListByMarket(LocalDateTime lastModifiedAt, Long lastCouponId, Integer size) {

        BooleanBuilder whereClause = booleanBuilderSubQuery();

        // lastModifiedAt 조건 추가
        if (lastModifiedAt != null && lastCouponId != null) {

            // LocalDateTime을 밀리초 단위로 전환 -> 쿠폰의 수정시간이 밀리초 단위로 순서가 바뀔 수 있기 때문.
            LocalDateTime truncatedTime = lastModifiedAt.truncatedTo(ChronoUnit.MILLIS);

            // 시간 정렬로 하였을 때, 더 이른 시간을 보여줌.
            whereClause.and(coupon.modifiedAt.loe(truncatedTime))
                    .and(coupon.isDeleted.eq(false))
                    .and(coupon.isHidden.eq(false));
        }

        return jpaQueryFactory
                .select(new QCouponMarketResDto(
                        market.id,
                        coupon.id,
                        market.name,
                        market.description,
                        market.thumbnail,
                        coupon.modifiedAt
                ))
                .from(coupon)
                .innerJoin(coupon.market, market)
                .where(whereClause)
                .orderBy(coupon.modifiedAt.desc()) // 최신순 정렬
                .limit(size + 1) // 다음 페이지 여부 확인용 1개 추가 조회
                .fetch();
    }


    @Override
    public List<CouponClosingTopResDto> findClosingTopCouponDtoList(Integer size) {

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

        return jpaQueryFactory.select(new QCouponClosingTopResDto(
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

    private BooleanBuilder booleanBuilderSubQuery() {
        BooleanBuilder whereClause = new BooleanBuilder();
        // 기본 조건
        whereClause.and(Expressions.list(coupon.market.id, coupon.modifiedAt).in(subQuery())); // 서브쿼리와 매칭

        return whereClause;
    }

    private JPQLQuery<Tuple> subQuery() {
        QCoupon subCoupon = new QCoupon("subCoupon");

        // 서브쿼리: 각 market_id 그룹별 최신 쿠폰의 modifiedAt을 구함
        JPQLQuery<Tuple> subQuery = JPAExpressions
                .select(subCoupon.market.id, subCoupon.modifiedAt.max())
                .from(subCoupon)
                .innerJoin(subCoupon.market, market)
                .where(subCoupon.isDeleted.eq(false)
                        .and(subCoupon.isHidden.eq(false)))
                .groupBy(subCoupon.market.id);

        return subQuery;
    }

}
