package com.appcenter.marketplace.domain.coupon.dto.res;

import com.appcenter.marketplace.domain.coupon.Coupon;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CouponResDto {
    private final Long couponId;
    private final String couponName;
    private final String description;
    private final LocalDateTime deadLine;
    private final int stock;
    private final boolean isHidden;
    private final LocalDateTime createdAt;

    @Builder
    private CouponResDto(Long couponId, String couponName, String description, LocalDateTime deadLine, int stock, boolean isHidden, LocalDateTime createdAt) {
        this.couponId = couponId;
        this.couponName = couponName;
        this.description = description;
        this.deadLine = deadLine;
        this.stock = stock;
        this.isHidden = isHidden;
        this.createdAt = createdAt;
    }

    public static CouponResDto toDto(Coupon coupon){
        return CouponResDto.builder()
                .couponId(coupon.getId())
                .couponName(coupon.getName())
                .description(coupon.getDescription())
                .deadLine(coupon.getDeadLine())
                .stock(coupon.getStock())
                .isHidden(coupon.getIsHidden())
                .createdAt(coupon.getCreatedAt())
                .build();
    }

}
