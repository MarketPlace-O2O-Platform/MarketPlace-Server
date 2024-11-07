package com.appcenter.marketplace.domain.market.repository;

import com.appcenter.marketplace.domain.image.dto.res.QImageResDto;
import com.appcenter.marketplace.domain.market.dto.res.MarketDetailResDto;
import com.appcenter.marketplace.domain.market.dto.res.MarketResDto;
import com.appcenter.marketplace.domain.market.dto.res.QMarketDetailResDto;
import com.appcenter.marketplace.domain.market.dto.res.QMarketResDto;
import com.appcenter.marketplace.global.common.StatusCode;
import com.appcenter.marketplace.global.exception.CustomException;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.util.List;

import static com.appcenter.marketplace.domain.image.QImage.image;
import static com.appcenter.marketplace.domain.market.QMarket.market;
import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;


@RequiredArgsConstructor
public class MarketRepositoryCustomImpl implements MarketRepositoryCustom{
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public MarketDetailResDto findMarketDetailResDtoById(Long marketId) {
        // market과 image를 조인 하여 매장 정보와 순서에 오름차순인 이미지 리스트를 dto에 매핑한다.
        List<MarketDetailResDto> marketDetailResDtoList = jpaQueryFactory
                .from(market)
                .leftJoin(image).on(image.market.id.eq(market.id))
                .where(market.id.eq(marketId)) // 매장 ID로 필터링
                .orderBy(image.sequence.asc())
                // transfrom을 통해 쿼리 결과를 원하는 형태로 변환한다.
                // groupBy(sql의 groupBy가 아니다)를 통해 마켓id를 기준으로 그룹화해 마켓 DTO List로 만든다.
                // 그룹화를 함으로 써 이미지 DTO 리스트를 list()로 받을 수 있게된다.
                .transform(groupBy(market.id).list(new QMarketDetailResDto(
                                market.id,
                                market.name,
                                market.description,
                                market.operationHours,
                                market.closedDays,
                                market.phoneNumber,
                                market.address,
                                list(new QImageResDto(image.id, image.sequence, image.name)
                        ))));

        if(marketDetailResDtoList.isEmpty())
            throw new CustomException(StatusCode.MARKET_NOT_EXIST);

        return marketDetailResDtoList.get(0);
    }

    @Override
    public Slice<MarketResDto> pagingMarketResDtoList(Long marketId, Pageable pageable) {
        // marketId 보다 작은 값으로부터 page.size+1까지의 데이터를 가져온다.
        // 내림차순으로 조회하는 이유는 보통 최신순으로 보여주기 때문이다.
        // page.size보다 1개 더 가져오는 이유는 다음 페이지가 존재하는지 확인하는 용도이다.
        List<MarketResDto> results= jpaQueryFactory
                .select(new QMarketResDto(market.id, market.name, market.description, market.thumbnail))
                .from(market)
                .where(ltMarketId(marketId))
                .orderBy(market.id.desc())
                .limit(pageable.getPageSize()+1)
                .fetch();

        boolean hasNext=false;

        // 가져온 갯수가 페이지 사이즈보다 많으면 다음 페이지가 있는 것이다.
        if(results.size()>pageable.getPageSize()){
            hasNext=true;
            results.remove(pageable.getPageSize());
        }

        return new SliceImpl<>(results,pageable,hasNext);
    }

    // BooleanExpression은 QueryDSL에서 조건을 표현하는 객체로, 조건이 참이면 해당 조건이 쿼리의 WHERE 절에 적용된다.
    // lt= less than = <(~보다 작은)
    private BooleanExpression ltMarketId(Long marketId){
        if(marketId==null)
            return null;
        else
            return market.id.lt(marketId);

    }
}
