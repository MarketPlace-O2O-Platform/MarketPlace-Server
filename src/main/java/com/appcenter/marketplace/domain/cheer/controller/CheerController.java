package com.appcenter.marketplace.domain.cheer.controller;

import com.appcenter.marketplace.domain.cheer.Cheer;
import com.appcenter.marketplace.domain.cheer.service.CheerService;
import com.appcenter.marketplace.global.common.CommonResponse;
import com.appcenter.marketplace.global.common.StatusCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.appcenter.marketplace.global.common.StatusCode.CHEER_SUCCESS;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cheer")
public class CheerController {

    private final CheerService cheerService;

    @PostMapping
    public ResponseEntity<CommonResponse<Object>> createCheer(
            @RequestParam Long memberId,
            @RequestParam Long tempMarketId
    ){
        cheerService.toggleCheerStatus(memberId, tempMarketId);
        return ResponseEntity.ok(CommonResponse.from(CHEER_SUCCESS.getMessage()));
    }
}
