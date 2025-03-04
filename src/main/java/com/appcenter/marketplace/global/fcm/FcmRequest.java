package com.appcenter.marketplace.global.fcm;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FcmRequest {
    Long memberId;
    String title;
    String body;

    @Builder
    public FcmRequest(Long memberId, String title, String body) {
        this.memberId = memberId;
        this.title = title;
        this.body = body;
    }
}

