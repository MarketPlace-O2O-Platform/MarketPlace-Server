package com.appcenter.marketplace.domain.market.controller;


import com.appcenter.marketplace.domain.market.dto.res.MarketDetailsResDto;
import com.appcenter.marketplace.domain.market.dto.res.MarketPageResDto;
import com.appcenter.marketplace.domain.market.service.MarketService;
import com.appcenter.marketplace.global.common.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.appcenter.marketplace.global.common.StatusCode.MARKET_FOUND;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/markets")
public class MarketController {
    private final MarketService marketService;

    @Operation(summary = "상세 매장 조회", description = "상세 매장 정보 및 이미지 리스트를 조회합니다.")
    @GetMapping("/{marketId}")
    public ResponseEntity<CommonResponse<MarketDetailsResDto>> getMarket(@PathVariable Long marketId){
        return ResponseEntity
                .ok(CommonResponse.from(MARKET_FOUND.getMessage(),marketService.getMarketDetails(marketId)));
    }

    // size가 null로 들어올 시 기본 값을 지정해주기 위해 integer로 값을 받아온다.
    @Operation(summary = "전체 매장 리스트 조회", description = "간단한 매장 정보 리스트를 반환합니다. <br>" +
            "무한 스크롤 방식으로 구현되어, 반환된 데이터의 마지막 marketId와 반환할 데이터의 size를 보내주면 됩니다. <br>" +
            "처음 요청 시엔 size만 보내주면 됩니다. <br>" +
            "지금은 최신순으로 보여줍니다. 또한 size의 기본값은 5입니다.")
    @GetMapping
    public ResponseEntity<CommonResponse<MarketPageResDto>> getMarket(
            @RequestParam(required = false, name = "lastMarketId") Long marketId, @RequestParam(defaultValue = "5") Integer size){
        return ResponseEntity
                .ok(CommonResponse.from(MARKET_FOUND.getMessage()
                        ,marketService.getMarketPage(marketId,size)));
    }
}
