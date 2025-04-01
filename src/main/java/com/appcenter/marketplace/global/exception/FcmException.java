package com.appcenter.marketplace.global.exception;

import com.appcenter.marketplace.global.common.StatusCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class FcmException extends RuntimeException{
    private final StatusCode statusCode;
}
