package com.appcenter.marketplace.domain.member_coupon.service.impl;

import com.appcenter.marketplace.domain.coupon.Coupon;
import com.appcenter.marketplace.domain.coupon.repository.CouponRepository;
import com.appcenter.marketplace.domain.member.Member;
import com.appcenter.marketplace.domain.member.MemberRepository;
import com.appcenter.marketplace.domain.member_coupon.MemberCoupon;
import com.appcenter.marketplace.domain.member_coupon.dto.res.IssuedMemberCouponResDto;
import com.appcenter.marketplace.domain.member_coupon.dto.res.MemberCouponListResDto;
import com.appcenter.marketplace.domain.member_coupon.dto.res.MemberCouponUpdateResDto;
import com.appcenter.marketplace.domain.member_coupon.repository.MemberCouponRepository;
import com.appcenter.marketplace.domain.member_coupon.dto.res.MemberCouponResDto;
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

    @Override
    @Transactional
    public MemberCouponListResDto getMemberCouponList(Long memberId) {
      return getMemberCouponList(memberId, memberCouponRepository::findIssuedCouponsByMemberId);
    }

    @Override
    @Transactional
    public MemberCouponListResDto getExpiredMemberCouponList(Long memberId) {
       return getMemberCouponList(memberId, memberCouponRepository::findExpiredCouponsByMemberId);
    }

    @Override
    @Transactional
    public MemberCouponListResDto getUsedMemberCouponList(Long memberId) {
        return getMemberCouponList(memberId, memberCouponRepository::findUsedCouponsByMemberId);
    }

    @Override
    @Transactional
    public MemberCouponUpdateResDto updateCoupon(Long memberCouponId) {
        MemberCoupon memberCoupon = findMemberCouponById(memberCouponId);
        memberCoupon.updateIsUsed();
        return MemberCouponUpdateResDto.toDto(memberCoupon);
    }

    @Override
    public IssuedMemberCouponResDto getMemberCoupon(Long memberCouponId) {
        MemberCoupon memberCoupon = findMemberCouponById(memberCouponId);
        return IssuedMemberCouponResDto.toDto(memberCoupon);
    }


    private MemberCouponListResDto getMemberCouponList(Long memberId, Function<Long, List<IssuedMemberCouponResDto>> findCoupons){
        Member member = findMemberById(memberId);
        List<IssuedMemberCouponResDto> memberCouponList = findCoupons.apply(member.getId());
        return MemberCouponListResDto.toDto(memberCouponList);
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

    private MemberCoupon findMemberCouponById(Long memberCouponId){
        return memberCouponRepository.findById(memberCouponId).orElseThrow(() -> new CustomException(COUPON_NOT_EXIST));
    }

}
