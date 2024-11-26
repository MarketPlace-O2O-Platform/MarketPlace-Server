package com.appcenter.marketplace.domain.market.dto.req;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MarketUpdateReq {
    @NotBlank(message = "매장명은 필수입력값입니다.")
    private String marketName;

    @NotBlank(message = "매장에 대한 설명은 필수입력값입니다.")
    private String description;

    private String operationHours;

    private String closedDays;

    private String phoneNumber;

    @NotBlank(message = "주소값은 필수입력값입니다.")
    private String address;

    @NotBlank(message = "카테고리 대분류는 필수입력값입니다.")
    private String major;
}
