package com.appcenter.marketplace.domain.market.repository;

import com.appcenter.marketplace.domain.coupon.QCoupon;
import com.appcenter.marketplace.domain.image.dto.res.QImageResDto;
import com.appcenter.marketplace.domain.market.dto.res.*;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.appcenter.marketplace.domain.category.QCategory.category;
import static com.appcenter.marketplace.domain.coupon.QCoupon.coupon;
import static com.appcenter.marketplace.domain.favorite.QFavorite.favorite;
import static com.appcenter.marketplace.domain.image.QImage.image;
import static com.appcenter.marketplace.domain.market.QMarket.market;
import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;


@RequiredArgsConstructor
public class MarketRepositoryCustomImpl implements MarketRepositoryCustom{
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<MarketDetailsResDto> findMarketDetailsResDtoListById(Long marketId) {
        // market과 image를 조인 하여 매장 정보와 순서에 오름차순인 이미지 리스트를 dto에 매핑한다.
        return  jpaQueryFactory
                .from(market)
                .innerJoin(image).on(market.eq(image.market))
                .where(market.id.eq(marketId)) // 매장 ID로 필터링
                .orderBy(image.sequence.asc())
                // transfrom을 통해 쿼리 결과를 원하는 형태로 변환한다.
                // groupBy(sql의 groupBy가 아니다)를 통해 마켓id를 기준으로 그룹화해 마켓 DTO List로 만든다.
                // 그룹화를 함으로 써 이미지 DTO 리스트를 list()로 받을 수 있게된다.
                .transform(groupBy(market.id).list(new QMarketDetailsResDto(
                                market.id,
                                market.name,
                                market.description,
                                market.operationHours,
                                market.closedDays,
                                market.phoneNumber,
                                market.address,
                                list(new QImageResDto(image.id, image.sequence, image.name)
                        ))));
    }


    // marketId 보다 작은 값으로부터 size+1까지의 데이터를 가져온다.
    // 내림차순으로 조회하는 이유는 보통 최신순으로 보여주기 때문이다.
    // size보다 1개 더 가져오는 이유는 다음 페이지가 존재하는지 확인하는 용도이다.
    @Override
    public List<MarketResDto> findMarketResDtoList(Long marketId, Integer size) {
        return jpaQueryFactory
                .select(new QMarketResDto(market.id, market.name, market.description, market.thumbnail))
                .from(market)
                .where(ltMarketId(marketId))
                .orderBy(market.id.desc())
                .limit(size + 1)
                .fetch();
    }

    @Override
    public List<MarketResDto> findMarketResDtoListByCategory(Long marketId, Integer size, String major) {
        return jpaQueryFactory
                .select(new QMarketResDto(market.id, market.name, market.description, market.thumbnail))
                .from(market)
                .innerJoin(category).on(market.category.eq(category))
                .where(ltMarketId(marketId).and(category.major.stringValue().eq(major)))
                .orderBy(market.id.desc())
                .limit(size + 1)
                .fetch();
    }

    @Override
    public List<MarketResDto> findFavoriteMarketResDtoByMemberId(Long memberId, Long marketId, Integer size) {
        return jpaQueryFactory
                .select(new QMarketResDto(market.id, market.name, market.description, market.thumbnail))
                .from(market)
                .innerJoin(favorite).on(market.eq(favorite.market))
                .where(ltMarketId(marketId).and(favorite.member.id.eq(memberId)).and(favorite.isDeleted.eq(false)))
                .orderBy(favorite.modifiedAt.desc())
                .limit(size + 1)
                .fetch();
    }

    @Override
    public List<MarketResDto> findTopFavoriteMarketResDto(Long marketId, Integer size) {
        return jpaQueryFactory
                .select(new QMarketResDto(market.id, market.name, market.description, market.thumbnail))
                .from(market)
                .leftJoin(favorite).on(market.eq(favorite.market)) // market과 favorite를 조인
                .where(ltMarketId(marketId).and(favorite.isDeleted.eq(false))) // 삭제되지 않은 찜만 필터링
                .groupBy(market.id) // 매장별로 그룹화
                .orderBy(favorite.member.id.count().desc()) // 찜 수가 많은 순으로 정렬
                .limit(size+1) // 반환할 리스트 크기 제한
                .fetch(); // 결과 반환
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
    public List<MarketCouponResDto> findLatestCouponMarketResDtoListByMarket(LocalDateTime lastModifiedAt, Long lastCouponId, Integer size) {
        BooleanBuilder whereClause = booleanBuilderSubQuery();

        // lastModifiedAt 조건 추가
        if (lastModifiedAt != null && lastCouponId != null) {

            // LocalDateTime을 밀리초 단위로 전환 -> 쿠폰의 수정시간이 밀리초 단위로 순서가 바뀔 수 있기 때문.
            LocalDateTime truncatedTime = lastModifiedAt.truncatedTo(ChronoUnit.MILLIS);

            // 시간 정렬로 하였을 때, 더 이른 시간을 보여줌.
            whereClause.and(coupon.modifiedAt.loe(truncatedTime))
                    .and(coupon.isDeleted.eq(false))
                    .and(coupon.isHidden.eq(false))
                    .and(coupon.deadLine.after(LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS)));
            ;
        }

        return jpaQueryFactory
                .select(new QMarketCouponResDto(
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

    // BooleanBuilder은 QueryDSL에서 조건을 표현하는 객체이다. 빈 객체를 반환하면 아무런 조건없이 실행된다.
    // lt= less than = <(~보다 작은)
    private BooleanBuilder ltMarketId(Long marketId){
        BooleanBuilder builder = new BooleanBuilder();  // 조건을 담을 BooleanBuilder 생성
        if (marketId != null) {
            builder.and(market.id.lt(marketId));  // marketId가 존재하면 lt 조건을 추가
        }
        return builder;  // 항상 유효한 BooleanExpression을 반환

    }

    private BooleanBuilder booleanBuilderSubQuery() {
        QCoupon subCoupon = new QCoupon("subCoupon");

        BooleanBuilder whereClause = new BooleanBuilder();

        // 서브쿼리: 각 market_id 그룹별 최신 쿠폰의 modifiedAt을 구함
        JPQLQuery<Tuple> subQuery = JPAExpressions
                .select(market.id, subCoupon.modifiedAt.max())
                .from(subCoupon)
                .innerJoin(subCoupon.market, market)
                .where(subCoupon.isDeleted.eq(false)
                        .and(subCoupon.isHidden.eq(false))
                        .and(subCoupon.deadLine.after(LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS))))
                .groupBy(market.id);

        // 기본 조건
        whereClause.and(Expressions.list(coupon.market.id, coupon.modifiedAt).in(subQuery)); // 서브쿼리와 매칭

        return whereClause;
    }
}
