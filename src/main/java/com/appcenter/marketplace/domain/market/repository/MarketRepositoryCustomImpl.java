package com.appcenter.marketplace.domain.market.repository;

import com.appcenter.marketplace.domain.image.dto.res.QImageResDto;
import com.appcenter.marketplace.domain.market.dto.res.MarketDetailsResDto;
import com.appcenter.marketplace.domain.market.dto.res.MarketResDto;
import com.appcenter.marketplace.domain.market.dto.res.QMarketDetailsResDto;
import com.appcenter.marketplace.domain.market.dto.res.QMarketResDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.appcenter.marketplace.domain.category.QCategory.category;
import static com.appcenter.marketplace.domain.image.QImage.image;
import static com.appcenter.marketplace.domain.market.QMarket.market;
import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;


@RequiredArgsConstructor
public class MarketRepositoryCustomImpl implements MarketRepositoryCustom{
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<MarketDetailsResDto> findMarketDetailResDtoListById(Long marketId) {
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
