package com.appcenter.marketplace.domain.member_coupon.repository;

import static com.appcenter.marketplace.domain.member_coupon.QMemberCoupon.memberCoupon;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MemberCouponRepositoryCustomImpl implements MemberCouponRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public boolean existCouponByMemberId(Long memberId, Long couponId) {

        // 해당 Id에 coupon이 존재하면 true, 중복되지 않으면 false
        return  jpaQueryFactory.selectFrom(memberCoupon)
                .where(memberCoupon.member.id.eq(memberId)
                        .and(memberCoupon.coupon.id.eq(couponId)))
                .fetchFirst() != null;
    }
}
