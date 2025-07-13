package com.appcenter.marketplace.domain.payback.dto.req;

import com.appcenter.marketplace.domain.market.Market;
import com.appcenter.marketplace.domain.payback.Payback;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 환급쿠폰
@Getter
@NoArgsConstructor
public class PaybackReq {

    @NotBlank(message = "쿠폰명은 필수입력값입니다.")
    private String couponName;

    @NotBlank(message = "쿠폰에 대한 설명은 필수입력값입니다.")
    private String description;

    @Builder
    private PaybackReq(String couponName, String description) {
        this.couponName = couponName;
        this.description = description;
    }

    public Payback ofCreate(Market market){
        return Payback.builder()
                .name(couponName)
                .description(description)
                .isHidden(true)
                .isDeleted(false)
                .market(market)
                .build();
    }
}
