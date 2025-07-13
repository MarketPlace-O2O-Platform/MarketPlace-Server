package com.appcenter.marketplace.domain.payback.repository;

import com.appcenter.marketplace.domain.payback.dto.res.PaybackRes;
import com.appcenter.marketplace.domain.payback.dto.res.QPaybackRes;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.appcenter.marketplace.domain.market.QMarket.market;
import static com.appcenter.marketplace.domain.payback.QPayback.payback;

@RequiredArgsConstructor
public class PaybackRepositoryCustomImpl implements PaybackRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<PaybackRes> findCouponsForAdminByMarketId(Long marketId, Long couponId, Integer size) {

        return queryFactory.select(new QPaybackRes(
                    payback.id,
                    payback.name,
                    payback.description,
                    payback.isHidden))
                .from(payback)
                .join(market).on(payback.market.id.eq(market.id))
                .where(ltCouponId(couponId)
                        .and(payback.market.id.eq(marketId))
                        .and(payback.isDeleted.eq(false)))
                .orderBy(payback.id.desc())
                .limit(size+1)
                .fetch();
    }

}
