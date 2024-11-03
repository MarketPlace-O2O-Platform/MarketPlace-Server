package com.appcenter.marketplace.domain.market.repository;

import com.appcenter.marketplace.domain.image.dto.res.QImageResDto;
import com.appcenter.marketplace.domain.market.dto.res.MarketResDto;
import com.appcenter.marketplace.domain.market.dto.res.QMarketResDto;
import com.appcenter.marketplace.global.common.StatusCode;
import com.appcenter.marketplace.global.exception.CustomException;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.appcenter.marketplace.domain.image.QImage.image;
import static com.appcenter.marketplace.domain.market.QMarket.market;
import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;


@RequiredArgsConstructor
public class MarketRepositoryCustomImpl implements MarketRepositoryCustom{
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public MarketResDto findMarketResDtoById(Long marketId) {
        List<MarketResDto> marketResDtoList= jpaQueryFactory
                .from(market)
                .leftJoin(image).on(image.market.id.eq(market.id))
                .where(market.id.eq(marketId)) // 매장 ID로 필터링
                .orderBy(image.sequence.asc())
                // transfrom을 통해 쿼리 결과를 원하는 형태로 변환한다.
                // groupBy(sql의 groupBy가 아니다)를 통해 마켓id를 기준으로 그룹화해 마켓 DTO List로 만든다.
                // 그룹화를 함으로 써 이미지 DTO 리스트를 list()로 받을 수 있게된다.
                .transform(groupBy(market.id).list(new QMarketResDto(
                                market.id,
                                market.name,
                                market.description,
                                market.operationHours,
                                market.closedDays,
                                market.phoneNumber,
                                market.address,
                                list(new QImageResDto(image.id, image.sequence, image.name)
                        ))));

        if(marketResDtoList.isEmpty())
            throw new CustomException(StatusCode.MARKET_NOT_EXIST);

        return marketResDtoList.get(0);
    }
}
