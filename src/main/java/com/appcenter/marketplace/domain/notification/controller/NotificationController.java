package com.appcenter.marketplace.domain.notification.controller;


import com.appcenter.marketplace.domain.notification.dto.req.NotificationReq;
import com.appcenter.marketplace.domain.notification.dto.res.NotificationRes;
import com.appcenter.marketplace.domain.notification.service.NotificationService;
import com.appcenter.marketplace.global.common.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import static com.appcenter.marketplace.global.common.StatusCode.NOTIFICATION_CREATE;

@Tag(name = "[알림 기록]", description = "[회원] 알림 기록 생성 및 조회")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notifications")
public class NotificationController {
    private final NotificationService notificationService;

    @Operation(summary = "알림 기록 생성", description = "알림 기록을 생성합니다.")
    @PostMapping
    public ResponseEntity<CommonResponse<NotificationRes>> createNotification(
            @AuthenticationPrincipal UserDetails userDetails, @RequestBody @Valid NotificationReq notificationReq) {

        Long memberId = Long.parseLong(userDetails.getUsername());
        return ResponseEntity.status(NOTIFICATION_CREATE.getStatus())
                .body(CommonResponse.from(NOTIFICATION_CREATE.getMessage(),notificationService.createNotification(memberId,notificationReq)));
    }



}
