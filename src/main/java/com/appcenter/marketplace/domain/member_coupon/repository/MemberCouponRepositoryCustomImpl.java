package com.appcenter.marketplace.domain.member_coupon.repository;

import com.appcenter.marketplace.domain.member_coupon.dto.res.IssuedCouponRes;
import com.appcenter.marketplace.domain.member_coupon.dto.res.QIssuedCouponRes;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import static com.appcenter.marketplace.domain.coupon.QCoupon.coupon;
import static com.appcenter.marketplace.domain.member_coupon.QMemberCoupon.memberCoupon;

@RequiredArgsConstructor
public class MemberCouponRepositoryCustomImpl implements MemberCouponRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public boolean existCouponByMemberId(Long memberId, Long couponId) {

        // 해당 Id에 coupon이 존재하면 true, 중복되지 않으면 false
        return jpaQueryFactory.selectFrom(memberCoupon)
                .where(memberCoupon.member.id.eq(memberId)
                        .and(memberCoupon.coupon.id.eq(couponId)))
                .fetchFirst() != null;
    }

    @Override
    public List<IssuedCouponRes> findIssuedCouponResDtoByMemberId(Long memberId) {
        // 만료되기 전의 쿠폰만 조회가 가능합니다.
        return findCouponsByMemberId(memberId, false);
    }

    @Override
    public List<IssuedCouponRes> findExpiredCouponResDtoByMemberId(Long memberId) {
        // 발급 받은 쿠폰 중, 기간이 만료된 쿠폰만 조회합니다.
        return findCouponsByMemberId(memberId, true);
    }

    @Override
    public List<IssuedCouponRes> findUsedMemberCouponResDtoByMemberId(Long memberId) {
        return jpaQueryFactory.select(new QIssuedCouponRes(memberCoupon.id,
                        coupon.id,
                        coupon.name,
                        coupon.description,
                        coupon.deadLine,
                        memberCoupon.isUsed))
                .from(coupon)
                .join(memberCoupon).on(memberCoupon.coupon.id.eq(coupon.id))
                .where(memberCoupon.member.id.eq(memberId)
                        .and(memberCoupon.isUsed.eq(true)))
                .fetch();
    }

    private List<IssuedCouponRes> findCouponsByMemberId(Long memberId, boolean isExpired) {
        return jpaQueryFactory.select(new QIssuedCouponRes(memberCoupon.id,
                        coupon.id,
                        coupon.name,
                        coupon.description,
                        coupon.deadLine,
                        memberCoupon.isUsed))
                .from(coupon)
                .join(memberCoupon).on(memberCoupon.coupon.id.eq(coupon.id))
                .where(memberCoupon.member.id.eq(memberId)
                        .and(memberCoupon.isUsed.eq(false))
                        .and(isExpired
                                ? memberCoupon.coupon.deadLine.before(LocalDateTime.now())
                                : memberCoupon.coupon.deadLine.after(LocalDateTime.now())))
                .fetch();

    }
}

