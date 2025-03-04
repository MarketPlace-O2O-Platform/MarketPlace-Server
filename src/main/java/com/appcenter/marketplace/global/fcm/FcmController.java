package com.appcenter.marketplace.global.fcm;

import com.appcenter.marketplace.global.common.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.appcenter.marketplace.global.common.StatusCode.MARKET_DELETE;

@Tag(name = "[알림TEST]", description = "[사장님,관리자] 알림 전송")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notification")
public class FcmController {
    private final FcmService fcmService;

    @Operation(summary = "[TEST] 개인 알림 전송", description = "개인 회원에게 알림 메시지 전송")
    @PostMapping
    public ResponseEntity<CommonResponse<Object>> sendFcmMessage(@RequestBody FcmRequest fcmRequest){
        fcmService.sendFcmMessage(fcmRequest);
        return ResponseEntity
                .ok(CommonResponse.from(MARKET_DELETE.getMessage()));
    }
}
