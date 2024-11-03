package com.appcenter.marketplace.domain.member_coupon.service.impl;

import com.appcenter.marketplace.domain.coupon.Coupon;
import com.appcenter.marketplace.domain.coupon.CouponRepository;
import com.appcenter.marketplace.domain.member.Member;
import com.appcenter.marketplace.domain.member.MemberRepository;
import com.appcenter.marketplace.domain.member_coupon.MemberCoupon;
import com.appcenter.marketplace.domain.member_coupon.repository.MemberCouponRepository;
import com.appcenter.marketplace.domain.member_coupon.dto.res.MemberCouponResDto;
import com.appcenter.marketplace.domain.member_coupon.repository.MemberCouponRepositoryCustomImpl;
import com.appcenter.marketplace.domain.member_coupon.service.MemberCouponService;
import com.appcenter.marketplace.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.appcenter.marketplace.global.common.StatusCode.*;

@Service
@RequiredArgsConstructor
public class MemberCouponServiceImpl implements MemberCouponService {

    private final MemberCouponRepository memberCouponRepository;
    private final CouponRepository couponRepository;
    private final MemberRepository memberRepository;
    private final MemberCouponRepositoryCustomImpl memberCouponRepositoryCustom;


    @Override
    @Transactional
    public MemberCouponResDto issuedCoupon(Long memberId, Long couponId) {
        // 해당 memberId롤 member 조회하는 로직은 -> 토큰 사용을 하면 수정할 예정입니다.
        Member member = findMemberById(memberId);
        Coupon coupon = findCouponById(couponId);

        if(!memberCouponRepository.existCouponByMemberId(member.getId(), coupon.getId())) {

            MemberCoupon memberCoupon = memberCouponRepository.save(MemberCoupon.builder()
                    .member(member)
                    .coupon(coupon)
                    .isUsed(false)
                    .build());
            coupon.updateStock();
            return MemberCouponResDto.toDto(memberCoupon);
        }
        else throw new CustomException(COUPON_ALREADY_ISSUED);
    }

    private Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(() -> new CustomException(MEMBER_NOT_EXIST));
    }

    private Coupon findCouponById(Long couponId) {
        Coupon coupon = couponRepository.findById(couponId).orElseThrow(() -> new CustomException(COUPON_NOT_EXIST));

        if(!coupon.getIsDeleted())
            return coupon;
        else throw new CustomException(COUPON_IS_DELETED);
    }


}
