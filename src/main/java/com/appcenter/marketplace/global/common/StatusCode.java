package com.appcenter.marketplace.global.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import static org.springframework.http.HttpStatus.*;

@Getter
@RequiredArgsConstructor
public enum StatusCode {

    // Market
    MARKET_CREATE(CREATED,"매장 생성 완료"),
    MARKET_FOUND(OK,"매장 조회 완료"),
    MARKET_UPDATE(OK,"매장 수정 완료"),
    MARKET_DELETE(OK,"매장 삭제 완료"),

    // Market
    COUPON_CREATE(CREATED,"쿠폰 생성 완료"),
    COUPON_FOUND(OK,"쿠폰 조회 완료"),
    COUPON_UPDATE(OK,"쿠폰 수정 완료"),
    COUPON_HIDDEN(OK,"쿠폰 숨김처리 완료"),
    COUPON_DELETE(OK,"쿠폰 삭제 완료"),

    /* 400 BAD_REQUEST : 잘못된 요청 */
    INPUT_VALUE_INVALID(BAD_REQUEST,"유효하지 않은 입력입니다.");

    private final HttpStatus status;
    private final String message;
}
