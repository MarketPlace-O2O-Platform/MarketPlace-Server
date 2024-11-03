package com.appcenter.marketplace.domain.member_coupon.repository;

import static com.appcenter.marketplace.domain.member_coupon.QMemberCoupon.memberCoupon;

import com.appcenter.marketplace.domain.member_coupon.dto.res.IssuedMemberCouponResDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

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

    @Override
    public List<IssuedMemberCouponResDto> findIssuedCouponsByMemberId(Long memberId) {
        // 만료되기 전의 쿠폰만 조회가 가능합니다.
        return findCouponsByMemberId(memberId, false);
    }

    @Override
    public List<IssuedMemberCouponResDto> findExpiredCouponsByMemberId(Long memberId) {
        // 발급 받은 쿠폰 중, 기간이 만료된 쿠폰만 조회합니다.
        return findCouponsByMemberId(memberId, true);
    }

    @Override
    public List<IssuedMemberCouponResDto> findUsedCouponsByMemberId(Long memberId) {
        return jpaQueryFactory.selectFrom(memberCoupon)
                .where(memberCoupon.member.id.eq(memberId)
                        .and(memberCoupon.isUsed.eq(true)))
                .fetch()
                .stream()
                .map(IssuedMemberCouponResDto::toDto).toList();
    }

    private List<IssuedMemberCouponResDto> findCouponsByMemberId(Long memberId, boolean isExpired) {
        return jpaQueryFactory.selectFrom(memberCoupon)
                .where(memberCoupon.member.id.eq(memberId)
                        .and(memberCoupon.isUsed.eq(false))
                        .and(isExpired
                                ? memberCoupon.coupon.deadLine.before(LocalDateTime.now())
                                : memberCoupon.coupon.deadLine.after(LocalDateTime.now())))
                .fetch()
                .stream()
                .map(IssuedMemberCouponResDto::toDto).toList();
    }

}
