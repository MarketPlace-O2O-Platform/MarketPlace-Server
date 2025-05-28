package com.appcenter.marketplace.global.jwt;

import com.appcenter.marketplace.domain.member.Member;
import com.appcenter.marketplace.domain.member.repository.MemberRepository;
import com.appcenter.marketplace.global.common.StatusCode;
import com.appcenter.marketplace.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    @Cacheable(value = "USER", key = "#memberId")
    public UserDetails loadUserByUsername(String memberId) {
        Member member = memberRepository.findById(Long.valueOf(memberId)).orElseThrow(
                () -> new CustomException(StatusCode.MEMBER_NOT_EXIST));
        return new CustomUserDetails(member);
    }
}
