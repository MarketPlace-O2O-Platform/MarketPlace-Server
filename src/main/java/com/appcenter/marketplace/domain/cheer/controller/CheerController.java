package com.appcenter.marketplace.domain.cheer.controller;

import com.appcenter.marketplace.domain.cheer.service.CheerService;
import com.appcenter.marketplace.global.common.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.appcenter.marketplace.global.common.StatusCode.CHEER_SUCCESS;

@Tag(name = "[공감]", description = "[공감] 임시 매장 공감하기")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cheer")
public class CheerController {

    private final CheerService cheerService;

    @Operation(summary = "공감탭 매장 공감하기", description = "임시 매장을 공감합니다. ")
    @PostMapping
    public ResponseEntity<CommonResponse<Object>> createCheer(
            @RequestParam Long memberId,
            @RequestParam Long tempMarketId
    ){
        cheerService.cheerTempMarket(memberId, tempMarketId);
        return ResponseEntity.ok(CommonResponse.from(CHEER_SUCCESS.getMessage()));
    }
}
