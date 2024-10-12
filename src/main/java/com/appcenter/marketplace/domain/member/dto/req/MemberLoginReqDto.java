package com.appcenter.marketplace.domain.member.dto.req;

import com.appcenter.marketplace.domain.member.Member;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberLoginReqDto {

    @NotBlank(message = "올바른 학번을 입력해주세요.")
    private String studentId;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;


    public Member toEntity(Long studentId) {
        return Member.builder()
                .id(studentId)
                .build();
    }
}
