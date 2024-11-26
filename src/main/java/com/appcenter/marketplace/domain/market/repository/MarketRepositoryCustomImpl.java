package com.appcenter.marketplace.domain.market.repository;

import com.appcenter.marketplace.domain.image.dto.res.QImageResDto;
import com.appcenter.marketplace.domain.market.dto.res.*;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import static com.appcenter.marketplace.domain.category.QCategory.category;
import static com.appcenter.marketplace.domain.coupon.QCoupon.coupon;
import static com.appcenter.marketplace.domain.favorite.QFavorite.favorite;
import static com.appcenter.marketplace.domain.image.QImage.image;
import static com.appcenter.marketplace.domain.local.QLocal.local;
import static com.appcenter.marketplace.domain.market.QMarket.market;
import static com.appcenter.marketplace.domain.metro.QMetro.metro;
import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;


@RequiredArgsConstructor
public class MarketRepositoryCustomImpl implements MarketRepositoryCustom{
    private final JPAQueryFactory jpaQueryFactory;

    // 상세 매장 정보 조회
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


    // 전체 매장 페이징 조회
    @Override
    public List<MarketResDto> findMarketResDtoList(Long memberId, Long marketId, Integer size) {
        return jpaQueryFactory
                .select(new QMarketResDto(
                        market.id,
                        market.name,
                        market.description,
                        metro.name.concat(" ").concat(local.name),
                        market.thumbnail,
                        favorite.id.isNotNull()))
                .from(market)
                .leftJoin(favorite).on(market.eq(favorite.market)
                        .and(favorite.member.id.eq(memberId)
                                .and(favorite.isDeleted.eq(false))))
                .innerJoin(local).on(market.local.eq(local))
                .innerJoin(metro).on(local.metro.eq(metro))
                .where(ltMarketId(marketId))
                .orderBy(market.id.desc())
                .limit(size + 1)
                .fetch();
    }

    // 카테고리 필터 매장 페이징 조회
    @Override
    public List<MarketResDto> findMarketResDtoListByCategory(Long memberId, Long marketId, Integer size, String major) {
        return jpaQueryFactory
                .select(new QMarketResDto(
                        market.id,
                        market.name,
                        market.description,
                        metro.name.concat(" ").concat(local.name),
                        market.thumbnail,
                        favorite.id.isNotNull()))
                .from(market)
                .leftJoin(favorite).on(market.eq(favorite.market)
                        .and(favorite.member.id.eq(memberId)
                                .and(favorite.isDeleted.eq(false))))
                .innerJoin(category).on(market.category.eq(category))
                .innerJoin(local).on(market.local.eq(local))
                .innerJoin(metro).on(local.metro.eq(metro))
                .where(ltMarketId(marketId).and(category.major.stringValue().eq(major)))
                .orderBy(market.id.desc())
                .limit(size + 1)
                .fetch();
    }

    // 회원이 찜한 매장 페이징 조회
    @Override
    public List<MyFavoriteMarketResDto> findFavoriteMarketResDtoByMemberId(Long memberId, LocalDateTime lastModifiedAt, Integer size) {
        return jpaQueryFactory
                .select(new QMyFavoriteMarketResDto(
                        market.id,
                        market.name,
                        market.description,
                        metro.name.concat(" ").concat(local.name),
                        market.thumbnail,
                        favorite.id.isNotNull(),
                        favorite.modifiedAt))
                .from(market)
                .innerJoin(favorite).on(market.eq(favorite.market)
                        .and(favorite.member.id.eq(memberId)
                                .and(favorite.isDeleted.eq(false))))
                .innerJoin(local).on(market.local.eq(local))
                .innerJoin(metro).on(local.metro.eq(metro))
                .where(ltFavoriteModifiedAt(lastModifiedAt))
                .orderBy(favorite.modifiedAt.desc())
                .limit(size + 1)
                .fetch();
    }

    // 찜 수가 가장 많은 매장 페이징 조회
    @Override
    public List<FavoriteMarketResDto> findFavoriteMarketResDto(Long memberId, Long count, Integer size) {
        return jpaQueryFactory
                .select(new QFavoriteMarketResDto(
                        market.id,
                        market.name,
                        market.description,
                        metro.name.concat(" ").concat(local.name),
                        market.thumbnail,
                        favorite.id.isNotNull(),
                        favorite.member.id.count()))
                .from(market)
                .leftJoin(favorite).on(market.eq(favorite.market)
                        .and(favorite.member.id.eq(memberId)
                                .and(favorite.isDeleted.eq(false))))
                .innerJoin(local).on(market.local.eq(local))
                .innerJoin(metro).on(local.metro.eq(metro))
                .where(loeFavoriteCount(count)) // 삭제되지 않은 찜만 필터링
                .groupBy(market.id, market.name, market.description, metro.name, local.name, market.thumbnail,favorite.id)
                .orderBy(favorite.member.id.count().desc(),market.id.desc()) // 찜 수가 많은 순으로 정렬
                .limit(size+1) // 반환할 리스트 크기 제한
                .fetch(); // 결과 반환
    }

    // 찜 수가 가장 많은 매장 Top 조회
    @Override
    public List<TopFavoriteMarketResDto> findTopFavoriteMarketResDto(Integer size) {
        return jpaQueryFactory
                .select(new QTopFavoriteMarketResDto(
                        market.id,
                        market.name,
                        coupon.id,
                        coupon.name,
                        market.thumbnail))
                .from(market)
                .leftJoin(favorite).on(market.eq(favorite.market)
                                .and(favorite.isDeleted.eq(false)))
                .leftJoin(coupon).on(market.eq(coupon.market))
                .groupBy(market.id, market.name, coupon.id, coupon.name, market.thumbnail)
                .orderBy(favorite.id.count().desc()) // 찜 수가 많은 순으로 정렬
                .limit(size)
                .fetch();
    }

    // BooleanExpression을 반환 시 where의 첫 조건에서 null 예외가 뜰 수 있다.
    // lt= less than = <(~보다 작은)
    private BooleanBuilder ltMarketId(Long marketId){
        BooleanBuilder builder = new BooleanBuilder();  // 조건을 담을 BooleanBuilder 생성
        if (marketId != null) {
            builder.and(market.id.lt(marketId));  // marketId가 존재하면 lt 조건을 추가
        }
        return builder;

    }

    // loe= less or equal = <=(~보다 작거나 같은)
    private BooleanBuilder loeFavoriteCount(Long count){
        BooleanBuilder builder = new BooleanBuilder();
        if (count != null) {
            builder.and(favorite.member.id.count().loe(count));
        }
        return builder;

    }

    private BooleanBuilder ltFavoriteModifiedAt(LocalDateTime modifiedAt){
        BooleanBuilder builder = new BooleanBuilder();
        if (modifiedAt != null) {
            builder.and(favorite.modifiedAt.lt(modifiedAt));
        }
        return builder;

    }
}
