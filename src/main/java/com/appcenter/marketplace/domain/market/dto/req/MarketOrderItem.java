package com.appcenter.marketplace.domain.market.dto.req;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MarketOrderItem {
    @NotNull(message = "매장 ID는 필수입력값입니다.")
    private Long marketId;

    @NotNull(message = "노출 순서는 필수입력값입니다.")
    private Integer orderNo;
}