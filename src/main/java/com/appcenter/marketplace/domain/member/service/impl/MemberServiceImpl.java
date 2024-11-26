package com.appcenter.marketplace.domain.member.service.impl;

import com.appcenter.marketplace.domain.member.Member;
import com.appcenter.marketplace.domain.member.MemberRepository;
import com.appcenter.marketplace.domain.member.dto.req.MemberLoginReq;
import com.appcenter.marketplace.domain.member.dto.res.MemberLoginRes;
import com.appcenter.marketplace.domain.member.service.MemberService;
import com.appcenter.marketplace.global.exception.CustomException;
import com.appcenter.marketplace.global.oracleRepository.InuLoginRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.appcenter.marketplace.global.common.StatusCode.INVALID_STUDENT_ID;
import static com.appcenter.marketplace.global.common.StatusCode.UNAUTHORIZED_LOGIN_ERROR;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final InuLoginRepository inuLoginRepository;
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public MemberLoginRes login(MemberLoginReq memberLoginReq) {
        Long studentId = validateAndParseStudentId(memberLoginReq);
        Member member = memberRepository.save(memberLoginReq.toEntity(studentId));
        return MemberLoginRes.toDto(member);
    }

    @Override
    @Transactional
    public MemberLoginRes getMember(Long studentId) {
        Member member = findMemberByMemberId(studentId);
        return MemberLoginRes.toDto(member);
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
