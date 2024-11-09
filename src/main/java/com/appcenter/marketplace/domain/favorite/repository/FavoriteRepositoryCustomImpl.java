package com.appcenter.marketplace.domain.favorite.repository;

import com.appcenter.marketplace.domain.market.dto.res.MarketResDto;
import com.appcenter.marketplace.domain.market.dto.res.QMarketResDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.appcenter.marketplace.domain.favorite.QFavorite.favorite;
import static com.appcenter.marketplace.domain.market.QMarket.market;

@RequiredArgsConstructor
public class FavoriteRepositoryCustomImpl implements FavoriteRepositoryCustom{
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<MarketResDto> findFavoriteMarketResDtoByMemberId(Long memberId, Long marketId, Integer size) {
        return jpaQueryFactory
                .select(new QMarketResDto(market.id, market.name, market.description, market.thumbnail))
                .from(market)
                .innerJoin(favorite).on(market.eq(favorite.market))
                .where(ltMarketId(marketId).and(favorite.member.id.eq(memberId)).and(favorite.isDeleted.eq(false)))
                .orderBy(market.id.desc())
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

    // BooleanBuilder은 QueryDSL에서 조건을 표현하는 객체이다. 빈 객체를 반환하면 아무런 조건없이 실행된다.
    // lt= less than = <(~보다 작은)
    private BooleanBuilder ltMarketId(Long marketId){
        BooleanBuilder builder = new BooleanBuilder();  // 조건을 담을 BooleanBuilder 생성
        if (marketId != null) {
            builder.and(market.id.lt(marketId));  // marketId가 존재하면 lt 조건을 추가
        }
        return builder;  // 항상 유효한 BooleanExpression을 반환

    }
}
