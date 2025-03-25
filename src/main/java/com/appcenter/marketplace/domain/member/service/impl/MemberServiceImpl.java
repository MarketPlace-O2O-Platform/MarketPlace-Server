package com.appcenter.marketplace.domain.member.service.impl;

import com.appcenter.marketplace.domain.member.Member;
import com.appcenter.marketplace.domain.member.dto.req.MemberLoginReq;
import com.appcenter.marketplace.domain.member.dto.res.MemberLoginRes;
import com.appcenter.marketplace.domain.member.repository.MemberRepository;
import com.appcenter.marketplace.domain.member.service.MemberService;
import com.appcenter.marketplace.global.exception.CustomException;
import com.appcenter.marketplace.global.oracleRepository.InuLoginRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.appcenter.marketplace.global.common.StatusCode.INVALID_STUDENT_ID;
import static com.appcenter.marketplace.global.common.StatusCode.UNAUTHORIZED_LOGIN_ERROR;


@Transactional(readOnly = true)
@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final InuLoginRepository inuLoginRepository;
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public MemberLoginRes login(MemberLoginReq memberLoginReq) {
        Long studentId = validateAndParseStudentId(memberLoginReq);

        Member existMember = memberRepository.findById(studentId).orElse(null);

        // 회원 정보 반환
        if( existMember != null){
            return MemberLoginRes.toDto(existMember);
        }

        // 회원 추가
        Member newMember = memberRepository.save(memberLoginReq.toEntity(studentId));
        return MemberLoginRes.toDto(newMember);
    }

    @Override
    public MemberLoginRes getMember(Long studentId) {
        Member member = findMemberByMemberId(studentId);
        return MemberLoginRes.toDto(member);
    }

    @Override
    @Transactional
    public long resetCheerTickets() {
            return memberRepository.resetCheerTickets();
    }

    @Override
    @Transactional
    public void permitFcm(Long memberId, String fcmToken) {
        Member member = findMemberByMemberId(memberId);
        member.permitFcmToken(fcmToken);
    }

    @Override
    @Transactional
    public void denyFcm(Long memberId) {
        Member member = findMemberByMemberId(memberId);
        member.denyFcmToken();
    }

    // 학번 로그인 검증 및 형변환
    private Long validateAndParseStudentId(MemberLoginReq memberLoginReq) {
        // 로그인 검증
        if(inuLoginRepository.loginCheck(memberLoginReq.getStudentId(), memberLoginReq.getPassword()))
            return Long.valueOf(memberLoginReq.getStudentId());
        else throw new CustomException(UNAUTHORIZED_LOGIN_ERROR);

    }

    private Member findMemberByMemberId(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(() -> new CustomException(INVALID_STUDENT_ID));
    }
}
