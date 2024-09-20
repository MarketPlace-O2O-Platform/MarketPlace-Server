package com.appcenter.marketplace.domain.coupon.dto.res;

import com.appcenter.marketplace.domain.coupon.domain.Coupon;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CouponInfoResDto {
    private final Long couponId;
    private final String couponName;
    private final String description;
    private final LocalDateTime deadLine;
    private final int stock;
    private final boolean isHidden;
    private final LocalDateTime createdAt;

    private CouponInfoResDto(Long couponId, String couponName, String description, LocalDateTime deadLine,int stock, boolean isHidden, LocalDateTime createdAt) {
        this.couponId = couponId;
        this.couponName = couponName;
        this.description = description;
        this.deadLine = deadLine;
        this.stock = stock;
        this.isHidden = isHidden;
        this.createdAt = createdAt;
    }

    public static CouponInfoResDto toDto(Coupon coupon){
        return new CouponInfoResDto(coupon.getId(), coupon.getName(), coupon.getDescription(), coupon.getDeadLine(),coupon.getStock(), coupon.getIsHidden(), coupon.getCreatedAt());
    }

}
