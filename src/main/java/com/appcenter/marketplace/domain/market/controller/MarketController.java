package com.appcenter.marketplace.domain.market.controller;


import com.appcenter.marketplace.domain.market.dto.res.MarketResDto;
import com.appcenter.marketplace.domain.market.service.MarketService;
import com.appcenter.marketplace.global.common.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.appcenter.marketplace.global.common.StatusCode.MARKET_FOUND;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/markets")
public class MarketController {
    private final MarketService marketService;

    @Operation(summary = "매장 조회", description = "매장 정보 및 이미지 리스트를 조회합니다.")
    @GetMapping("/{marketId}")
    public ResponseEntity<CommonResponse<MarketResDto>> getMarket(@PathVariable Long marketId){
        return ResponseEntity
                .ok(CommonResponse.from(MARKET_FOUND.getMessage(),marketService.getMarket(marketId)));
    }
}
