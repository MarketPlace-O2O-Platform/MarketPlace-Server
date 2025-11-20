package com.appcenter.marketplace.domain.cheer.controller;

import com.appcenter.marketplace.domain.cheer.dto.CheerCountRes;
import com.appcenter.marketplace.domain.cheer.service.CheerService;
import com.appcenter.marketplace.global.common.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import static com.appcenter.marketplace.global.common.StatusCode.CHEER_SUCCESS;

@Tag(name = "[공감]", description = "[공감] 임시 매장 공감하기")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cheer")
public class CheerController {

    private final CheerService cheerService;

    @Operation(summary = "공감탭 매장 공감하기",
            description = "임시 매장을 공감합니다. <br><br>" +
                    "**공감권 정책** <br>" +
                    "- 공감권은 매일 3개씩 충전됩니다. (개발 상태 : 10개) <br>" +
                    "- 3개의 매장을 공감하게 되면, 더이상 공감할 수 없습니다. <br><br>" +
                    "**성공 응답 (200 OK)** <br>" +
                    "- 공감 완료 후 해당 매장의 총 공감 수(cheerCount)를 반환합니다. <br><br>" +
                    "**에러 응답** <br>" +
                    "- 409 Conflict: 이미 공감한 매장입니다. <br>" +
                    "- 409 Conflict: 공감권이 소진되었습니다. <br>" +
                    "- 404 Not Found: 존재하지 않는 매장입니다.")
    @PostMapping
    public ResponseEntity<CommonResponse<CheerCountRes>> createCheer(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam Long tempMarketId
    ){
        Long memberId = Long.parseLong(userDetails.getUsername());
        return ResponseEntity.ok(CommonResponse.from(CHEER_SUCCESS.getMessage(), cheerService.cheerTempMarket(memberId, tempMarketId)));
    }
}
