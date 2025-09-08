package com.appcenter.marketplace.domain.member_payback.service.impl;

import com.appcenter.marketplace.domain.coupon.dto.res.CouponPageRes;
import com.appcenter.marketplace.domain.member.Member;
import com.appcenter.marketplace.domain.member.repository.MemberRepository;
import com.appcenter.marketplace.domain.member_coupon.MemberCouponType;
import com.appcenter.marketplace.domain.member_coupon.dto.res.CouponHandleRes;
import com.appcenter.marketplace.domain.member_coupon.dto.res.IssuedCouponRes;
import com.appcenter.marketplace.domain.member_payback.MemberPayback;
import com.appcenter.marketplace.domain.member_payback.dto.res.ReceiptRes;
import com.appcenter.marketplace.domain.member_payback.repository.MemberPaybackRepository;
import com.appcenter.marketplace.domain.member_payback.service.MemberPaybackService;
import com.appcenter.marketplace.domain.payback.Payback;
import com.appcenter.marketplace.domain.payback.repository.PaybackRepository;
import com.appcenter.marketplace.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static com.appcenter.marketplace.global.common.StatusCode.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberPaybackServiceImpl implements MemberPaybackService {

    private final MemberPaybackRepository memberPaybackRepository;
    private final MemberRepository memberRepository;
    private final PaybackRepository paybackRepository;

    @Value("${receipt.upload.path}")
    private String uploadFolder;

    @Override
    @Transactional
    public void issuedCoupon(Long memberId, Long paybackId) {
        Member member = findMemberById(memberId);
        Payback payback = findPaybackById(paybackId);

        if(!memberPaybackRepository.existCouponByMemberId(member.getId(), payback.getId())){
            memberPaybackRepository.save(
                    MemberPayback.builder()
                            .member(member)
                            .payback(payback)
                            .isPayback(false)
                            .isExpired(false)
                            .build()
            );
        }else{
            throw new CustomException(COUPON_ALREADY_ISSUED);
        }
    }

    @Override
    public CouponPageRes<IssuedCouponRes> getPaybackCouponList(Long memberId, MemberCouponType type, Long memberPaybackId, Integer size) {
        List<IssuedCouponRes> couponList;

        if (type == MemberCouponType.ISSUED){
             couponList = memberPaybackRepository.findIssuedCouponResByMemberId(memberId, memberPaybackId, size);
        }else{
            couponList = memberPaybackRepository.findEndedCouponResByMemberId(memberId, memberPaybackId, size);
        }

        return checkNextPageAndReturn(couponList, size);
    }

    @Override
    @Transactional
    public CouponHandleRes updateCoupon(Long memberId, Long memberPaybackId, MultipartFile receiptImage) {

        // 쿠폰 영수증 제출
        MemberPayback memberPayback = memberPaybackRepository
                .findByCouponIdAndMemberId(memberId, memberPaybackId)
                .orElseThrow(() -> new CustomException(FORBIDDEN));

        // 만료되었거나, 이미 환급되었으면 막힘.
        if (Boolean.TRUE.equals(memberPayback.getIsExpired()))  throw new CustomException(COUPON_IS_EXPIRED);
        if (Boolean.TRUE.equals(memberPayback.getIsPayback()))  throw new CustomException(COUPON_ALREADY_USED);

        String receiptPath = saveReceiptImages(receiptImage);

        memberPayback.updateReceipt(receiptPath);

        return CouponHandleRes.toDto(memberPayback);
    }

    @Override
    public ReceiptRes getReceipt(Long memberId, Long memberPaybackId) {
        return memberPaybackRepository.findReceiptByMemberId(memberId, memberPaybackId);
    }

    @Override
    @Transactional
    public void check3DaysCoupons() {
        // 스케줄러로 3일뒤 만료되도록(is_expired) 일괄 처리
        memberPaybackRepository.check3DaysCoupons();
    }


    private Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(() -> new CustomException(MEMBER_NOT_EXIST));
    }

    private Payback findPaybackById(Long paybackId) {
        Payback payback = paybackRepository.findById(paybackId).orElseThrow(() -> new CustomException(COUPON_NOT_EXIST));

        if (!payback.getIsDeleted())
            return payback;
        else throw new CustomException(COUPON_IS_DELETED);
    }

    private <T> CouponPageRes<T> checkNextPageAndReturn(List<T> couponList, Integer size) {
        boolean hasNext = false;

        if(couponList.size() > size){
            hasNext = true;
            couponList.remove(size.intValue());
        }

        return new CouponPageRes<>(couponList, hasNext);
    }

    private String saveReceiptImages(MultipartFile receiptImage) {
        if (receiptImage == null || receiptImage.isEmpty()) {
            throw new CustomException(INPUT_VALUE_INVALID);
        }

        String receiptFileName = UUID.randomUUID() + "_" + receiptImage.getOriginalFilename();

        try {
            File uploadDir = new File(uploadFolder);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }
            
            File uploadFile = new File(uploadFolder + receiptFileName);
            receiptImage.transferTo(uploadFile);
            return receiptFileName;
        } catch (IOException e) {
            throw new CustomException(FILE_SAVE_INVALID);
        }
    }
}
