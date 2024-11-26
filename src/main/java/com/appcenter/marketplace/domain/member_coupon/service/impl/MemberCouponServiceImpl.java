package com.appcenter.marketplace.domain.member_coupon.service.impl;

import com.appcenter.marketplace.domain.coupon.Coupon;
import com.appcenter.marketplace.domain.coupon.repository.CouponRepository;
import com.appcenter.marketplace.domain.member.Member;
import com.appcenter.marketplace.domain.member.MemberRepository;
import com.appcenter.marketplace.domain.member_coupon.MemberCoupon;
import com.appcenter.marketplace.domain.member_coupon.dto.res.IssuedCouponRes;
import com.appcenter.marketplace.domain.member_coupon.dto.res.CouponHandleRes;
import com.appcenter.marketplace.domain.member_coupon.repository.MemberCouponRepository;
import com.appcenter.marketplace.domain.member_coupon.service.MemberCouponService;
import com.appcenter.marketplace.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Function;

import static com.appcenter.marketplace.global.common.StatusCode.*;

@Service
@RequiredArgsConstructor
public class MemberCouponServiceImpl implements MemberCouponService {

    private final MemberCouponRepository memberCouponRepository;
    private final CouponRepository couponRepository;
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public void issuedCoupon(Long memberId, Long couponId) {
        Member member = findMemberById(memberId);
        Coupon coupon = findCouponById(couponId);

        // 쿠폰의 잔여 갯수가 0개일 경우
        if (coupon.getStock() == 0 ){
            throw new CustomException(COUPON_SOLD_OUT);
        }

        // 회원이 이미 해당 쿠폰을 발급받았는지 확인
        if (!memberCouponRepository.existCouponByMemberId(member.getId(), coupon.getId())) {
            MemberCoupon memberCoupon = memberCouponRepository.save(MemberCoupon.builder()
                    .member(member)
                    .coupon(coupon)
                    .isUsed(false)
                    .build());
            coupon.reduce();

        } else {
            throw new CustomException(COUPON_ALREADY_ISSUED);
        }
    }

    @Override
    @Transactional
    public List<IssuedCouponRes> getMemberCouponList(Long memberId) {
        return getMemberCouponList(memberId, memberCouponRepository::findIssuedCouponResDtoByMemberId);
    }

    @Override
    @Transactional
    public List<IssuedCouponRes> getExpiredMemberCouponList(Long memberId) {
        return getMemberCouponList(memberId, memberCouponRepository::findExpiredCouponResDtoByMemberId);
    }

    @Override
    @Transactional
    public List<IssuedCouponRes> getUsedMemberCouponList(Long memberId) {
        return getMemberCouponList(memberId, memberCouponRepository::findUsedMemberCouponResDtoByMemberId);
    }

    @Override
    @Transactional
    public CouponHandleRes updateCoupon(Long memberCouponId) {
        MemberCoupon memberCoupon = findMemberCouponById(memberCouponId);
        memberCoupon.usedToggle();
        return CouponHandleRes.toDto(memberCoupon);
    }

    @Override
    public IssuedCouponRes getMemberCoupon(Long memberCouponId) {
        MemberCoupon memberCoupon = findMemberCouponById(memberCouponId);
        return IssuedCouponRes.toDto(memberCoupon);
    }

    private List<IssuedCouponRes> getMemberCouponList(Long memberId, Function<Long, List<IssuedCouponRes>> findCoupons) {
        Member member = findMemberById(memberId);
        return findCoupons.apply(member.getId());
    }

    private Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(() -> new CustomException(MEMBER_NOT_EXIST));
    }

    private Coupon findCouponById(Long couponId) {
        Coupon coupon = couponRepository.findById(couponId).orElseThrow(() -> new CustomException(COUPON_NOT_EXIST));

        if (!coupon.getIsDeleted())
            return coupon;
        else throw new CustomException(COUPON_IS_DELETED);
    }

    private MemberCoupon findMemberCouponById(Long memberCouponId) {
        return memberCouponRepository.findById(memberCouponId).orElseThrow(() -> new CustomException(COUPON_NOT_EXIST));
    }
}