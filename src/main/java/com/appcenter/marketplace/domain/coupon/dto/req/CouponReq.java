package com.appcenter.marketplace.domain.coupon.dto.req;

import com.appcenter.marketplace.domain.coupon.Coupon;
import com.appcenter.marketplace.domain.market.Market;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CouponReq {
    @NotBlank(message = "쿠폰명은 필수입력값입니다.")
    private String couponName;

    @NotBlank(message = "쿠폰에 대한 설명은 필수입력값입니다.")
    private String description;

    @NotNull(message = "쿠폰의 마감날짜를 입력해주세요.")
    private LocalDateTime deadLine;

    @NotNull(message = "해당 쿠폰의 최대 갯수를 입력해주세요.")
    private int stock;

    @Builder
    public CouponReq(String couponName, String description, LocalDateTime deadLine, int stock) {
        this.couponName = couponName;
        this.description = description;
        this.deadLine = deadLine;
        this.stock = stock;
    }

    public Coupon ofCreate(Market market) {
        return Coupon.builder()
                .name(couponName)
                .description(description)
                .deadLine(deadLine)
                .stock(stock)
                .market(market)
                .isHidden(true)
                .isDeleted(false)
                .build();
    }

}
