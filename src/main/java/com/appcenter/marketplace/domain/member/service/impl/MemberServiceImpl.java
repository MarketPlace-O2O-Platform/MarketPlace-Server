package com.appcenter.marketplace.domain.member.service.impl;

import com.appcenter.marketplace.domain.member.Member;
import com.appcenter.marketplace.domain.member.MemberRepository;
import com.appcenter.marketplace.domain.member.dto.req.MemberLoginReqDto;
import com.appcenter.marketplace.domain.member.dto.res.MemberLoginResDto;
import com.appcenter.marketplace.domain.member.service.MemberService;
import static com.appcenter.marketplace.global.common.StatusCode.*;
import com.appcenter.marketplace.global.exception.CustomException;
import com.appcenter.marketplace.global.oracleRepository.InuLoginRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final InuLoginRepository inuLoginRepository;
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public MemberLoginResDto login(MemberLoginReqDto memberLoginReqDto) {
        Long studentId = validateAndParseStudentId(memberLoginReqDto);
        Member member = memberRepository.save(memberLoginReqDto.toEntity(studentId));
        return MemberLoginResDto.toDto(member);
    }

    // 학번 로그인 검증 및 형변환
    private Long validateAndParseStudentId(MemberLoginReqDto memberLoginReqDto) {
        // 로그인 검증
        if(!inuLoginRepository.loginCheck(memberLoginReqDto.getStudentId(), memberLoginReqDto.getPassword())) {
            throw new CustomException(UNAUTHORIZED_LOGIN_ERROR);
        }
        try {
            // 형변환
            return Long.valueOf(memberLoginReqDto.getStudentId());
        }catch (Exception e){
            throw new CustomException(INVALID_STUDENT_ID);
        }
    }

}
