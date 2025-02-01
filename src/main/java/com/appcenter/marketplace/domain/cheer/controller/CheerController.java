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

    @Operation(summary = "공감탭 매장 공감하기", description = "임시 매장을 공감합니다. <br>" +
            "공감권은 매일 1개씩 충전됩니다. 따라서, 1개의 매장을 공감하게 되면, 더이상 공감할 수 없습니다.( 에러 처리 (409) : 공감권이 소진되었습니다)")
    @PostMapping
    public ResponseEntity<CommonResponse<Object>> createCheer(
            @RequestParam Long memberId,
            @RequestParam Long tempMarketId
    ){
        cheerService.cheerTempMarket(memberId, tempMarketId);
        return ResponseEntity.ok(CommonResponse.from(CHEER_SUCCESS.getMessage()));
    }
}
