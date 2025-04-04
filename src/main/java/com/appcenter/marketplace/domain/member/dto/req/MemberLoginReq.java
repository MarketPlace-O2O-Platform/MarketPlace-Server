package com.appcenter.marketplace.domain.member.dto.req;

import com.appcenter.marketplace.domain.member.Member;
import com.appcenter.marketplace.domain.member.Role;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberLoginReq {

    @NotBlank(message = "학번은 필수 입력값입니다.")
    private String studentId;

    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    private String password;


    public Member toEntity(Long studentId) {
        return Member.builder()
                .id(studentId)
                .cheerTicket(1000) // 1로 수정 예정
                .role(Role.ROLE_USER)
                .build();
    }
}
