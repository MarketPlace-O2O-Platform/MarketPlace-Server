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
    MARKET_IMAGE_UPDATE(OK,"매장 이미지 리스트 수정 완료"),
    MARKET_DELETE(OK,"매장 삭제 완료"),
    MARKET_ALREADY_EXISTS(BAD_REQUEST, "이미 존재하는 매장입니다."),
    MARKET_HIDDEN(OK, "매장 숨김(공개) 처리 완료"),

    // Coupon
    COUPON_CREATE(CREATED,"쿠폰 생성 완료"),
    COUPON_ISSUED(OK,"쿠폰 발급 완료"),
    COUPON_FOUND(OK,"쿠폰 조회 완료"),
    COUPON_UPDATE(OK,"쿠폰 수정 완료"),
    COUPON_HIDDEN(OK,"쿠폰 숨김(공개)처리 완료"),
    COUPON_USED(OK,"쿠폰 사용처리 완료"),
    COUPON_DELETE(OK,"쿠폰 삭제 완료"),

    // Favorite
    FAVORITE_CREATE(CREATED,"찜 생성(취소) 완료"),

    // Cheer
    CHEER_SUCCESS(OK, "공감 확인(취소) 완료"),

    // Member
    MEMBER_LOGIN_SUCCESS(OK, "로그인에 성공하였습니다."),
    MEMBER_FOUND(OK, "회원 조회 완료"),



    /* 400 BAD_REQUEST : 잘못된 요청 */
    INPUT_VALUE_INVALID(BAD_REQUEST,"유효하지 않은 입력입니다."),
    INVALID_STUDENT_ID(BAD_REQUEST,"유효하지 않은 학번입니다."),
    ADDRESS_INVALID(BAD_REQUEST,"유효하지 않은 주소입니다."),
    MULTI_PART_FILE_INVALID(BAD_REQUEST,"MultiPartFile이 존재하지 않습니다"),
    MULTI_PART_FILE_SEQUENCE_INVALID(BAD_REQUEST,"MultipartFile과 순서 리스트의 개수가 맞지 않습니다."),
    FILE_SAVE_INVALID(BAD_REQUEST,"파일 저장 중 오류가 발생했습니다."),
    FILE_DELETE_INVALID(BAD_REQUEST,"파일 삭제 중 오류가 발생했습니다."),
    DATA_INTEGRITY_VIOLATION(BAD_REQUEST,"이미 존재하는 값이거나 필수 필드에 null이 있습니다."),
    MARKET_SEARCH_NAME_INVALID(BAD_REQUEST,"검색어는 2글자 이상 입력해야 합니다."),

    /* 401 UNAUTHORIZED : 비인증 사용자 */
    UNAUTHORIZED_LOGIN_ERROR(UNAUTHORIZED, "학번 또는 비밀번호가 올바르지 않습니다."),

    /* 403 FORBIDDEN : 권한 없음 */

    /* 404 NOT_FOUND : 존재하지 않는 리소스 */
    MEMBER_NOT_EXIST(NOT_FOUND, "존재하지 않는 회원입니다."),
    CATEGORY_NOT_EXIST(NOT_FOUND,"존재하지 않는 카테고리입니다."),
    MARKET_NOT_EXIST(NOT_FOUND,"존재하지 않는 매장입니다."),
    COUPON_NOT_EXIST(NOT_FOUND, "존재하지 않는 쿠폰입니다."),
    COUPON_IS_DELETED(NOT_FOUND, "이미 삭제된 쿠폰입니다."),
    IMAGE_NOT_EXIST(NOT_FOUND,"존재하지 않는 이미지입니다."),
    ADDRESS_NOT_EXIST(NOT_FOUND,"존재하지 않는 주소입니다."),

    /* 409 CONFLICT : 리소스 충돌 */
    COUPON_ALREADY_ISSUED(CONFLICT, "이미 발급된 쿠폰입니다."),
    COUPON_SOLD_OUT(CONFLICT, "쿠폰이 모두 소진되었습니다.");


    private final HttpStatus status;
    private final String message;
}
