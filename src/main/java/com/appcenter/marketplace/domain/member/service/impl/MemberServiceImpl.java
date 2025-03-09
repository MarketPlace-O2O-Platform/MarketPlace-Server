package com.appcenter.marketplace.domain.member.service.impl;

import com.appcenter.marketplace.domain.beta.BetaCoupon;
import com.appcenter.marketplace.domain.beta.BetaMarket;
import com.appcenter.marketplace.domain.beta.repository.BetaCouponRepository;
import com.appcenter.marketplace.domain.beta.repository.BetaMarketRepository;
import com.appcenter.marketplace.domain.member.Member;
import com.appcenter.marketplace.domain.member.repository.MemberRepository;
import com.appcenter.marketplace.domain.member.dto.req.MemberLoginReq;
import com.appcenter.marketplace.domain.member.dto.res.MemberLoginRes;
import com.appcenter.marketplace.domain.member.repository.MemberRepository;
import com.appcenter.marketplace.domain.member.service.MemberService;
import com.appcenter.marketplace.global.exception.CustomException;
import com.appcenter.marketplace.global.jwt.JwtTokenProvider;
import com.appcenter.marketplace.global.oracleRepository.InuLoginRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.appcenter.marketplace.global.common.StatusCode.INVALID_STUDENT_ID;
import static com.appcenter.marketplace.global.common.StatusCode.UNAUTHORIZED_LOGIN_ERROR;


@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final InuLoginRepository inuLoginRepository;
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final BetaMarketRepository betaMarketRepository;
    private final BetaCouponRepository betaCouponRepository;

    @Override
    @Transactional
    public String login(MemberLoginReq memberLoginReq) {
        Long studentId = validateAndParseStudentId(memberLoginReq);
//        Long studentId = Long.parseLong(memberLoginReq.getStudentId()); // 로컬 테스트 시
        Member existMember = memberRepository.findById(studentId).orElse(null);

        // 회원 정보 반환
        if( existMember != null){
            return jwtTokenProvider.createAccessToken(existMember.getId(), existMember.getRole().name());
        }

        // 회원 추가
        Member newMember = memberRepository.save(memberLoginReq.toEntity(studentId));
        sendAllCouponsToMember(newMember);

        return jwtTokenProvider.createAccessToken(newMember.getId(), newMember.getRole().name());
    }

    @Override
    @Transactional
    public MemberLoginRes getMember(Long studentId) {
        Member member = findMemberByMemberId(studentId);
        return MemberLoginRes.toDto(member);
    }

    @Override
    @Transactional
    public long resetCheerTickets() {
            return memberRepository.resetCheerTickets();
    }

    // 학번 로그인 검증 및 형변환
    private Long validateAndParseStudentId(MemberLoginReq memberLoginReq) {
        // 로그인 검증
        if(inuLoginRepository.loginCheck(memberLoginReq.getStudentId(), memberLoginReq.getPassword()))
            return Long.valueOf(memberLoginReq.getStudentId());
        else throw new CustomException(UNAUTHORIZED_LOGIN_ERROR);

    }

    // 회원가입 시 모든 쿠폰을 유저에게 전송
    public void sendAllCouponsToMember(Member member) {
        List<BetaMarket> betaMarketList= betaMarketRepository.findAll();
        List<BetaCoupon> betaCouponList = new ArrayList<>();

        for (BetaMarket betaMarket : betaMarketList) {
            betaCouponList.add(BetaCoupon.builder()
                    .isUsed(false)
                    .member(member)
                    .betaMarket(betaMarket)
                    .build());
        }

        betaCouponRepository.saveAll(betaCouponList);
    }

    private Member findMemberByMemberId(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(() -> new CustomException(INVALID_STUDENT_ID));
    }
}
