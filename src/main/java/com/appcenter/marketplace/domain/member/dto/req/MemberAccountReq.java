package com.appcenter.marketplace.domain.member.dto.req;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberAccountReq {

    @NotBlank(message = "주거래은행은 필수 입력값입니다.")
    private String account;

    @NotBlank(message = "계좌번호는 필수 입력값입니다.")
    private String accountNumber;
}
