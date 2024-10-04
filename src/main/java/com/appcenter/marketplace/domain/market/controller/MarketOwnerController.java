package com.appcenter.marketplace.domain.market.controller;

import com.appcenter.marketplace.domain.market.dto.req.MarketCreateReqDto;
import com.appcenter.marketplace.domain.market.dto.req.MarketUpdateReqDto;
import com.appcenter.marketplace.domain.market.dto.res.MarketResDto;
import com.appcenter.marketplace.domain.market.service.MarketOwnerService;
import com.appcenter.marketplace.global.common.CommonResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.appcenter.marketplace.global.common.StatusCode.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/owners/markets")
public class MarketOwnerController {
    private final MarketOwnerService marketOwnerService;

    @PostMapping
    public ResponseEntity<CommonResponse<MarketResDto>> createMarket(@RequestBody @Valid MarketCreateReqDto marketCreateReqDto){
        return ResponseEntity
                .status(MARKET_CREATE.getStatus())
                .body(CommonResponse.from(MARKET_CREATE.getMessage(),marketOwnerService.createMarket(marketCreateReqDto)));
    }

    @PutMapping("/{marketId}")
    public ResponseEntity<CommonResponse<MarketResDto>> updateMarket(@RequestBody @Valid MarketUpdateReqDto marketUpdateReqDto,
                                                     @PathVariable Long marketId) {
        return ResponseEntity.ok(CommonResponse.from(MARKET_UPDATE.getMessage(),marketOwnerService.updateMarket(marketUpdateReqDto,marketId)));
    }

    @GetMapping("/{marketId}")
    public ResponseEntity<CommonResponse<MarketResDto>> getMarket(@PathVariable Long marketId){
        return ResponseEntity.ok(CommonResponse.from(MARKET_FOUND.getMessage(),marketOwnerService.getMarket(marketId)));
    }

    @DeleteMapping("/{marketId}")
    public ResponseEntity<CommonResponse<Object>> deleteMarket(@PathVariable Long marketId){
        marketOwnerService.deleteMarket(marketId);
        return ResponseEntity.ok(CommonResponse.from(MARKET_DELETE.getMessage()));
    }

}
