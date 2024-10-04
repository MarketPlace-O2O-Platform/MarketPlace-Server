package com.appcenter.marketplace.domain.market.controller;

import com.appcenter.marketplace.domain.market.dto.req.MarketCreateReqDto;
import com.appcenter.marketplace.domain.market.dto.req.MarketUpdateReqDto;
import com.appcenter.marketplace.domain.market.dto.res.MarketResDto;
import com.appcenter.marketplace.domain.market.service.MarketOwnerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/owners/markets")
public class MarketOwnerController {
    private final MarketOwnerService marketOwnerService;

    @PostMapping
    public ResponseEntity<MarketResDto> createMarket(@RequestBody @Valid MarketCreateReqDto marketCreateReqDto){
        return ResponseEntity
                .status(201)
                .body(marketOwnerService.createMarket(marketCreateReqDto));
    }

    @PutMapping("/{marketId}")
    public ResponseEntity<MarketResDto> updateMarket(@RequestBody @Valid MarketUpdateReqDto marketUpdateReqDto,
                                                     @PathVariable Long marketId) {
        return ResponseEntity.ok(marketOwnerService.updateMarket(marketUpdateReqDto,marketId));
    }

    @GetMapping("/{marketId}")
    public ResponseEntity<MarketResDto> getMarket(@PathVariable Long marketId){
        return ResponseEntity.ok(marketOwnerService.getMarket(marketId));
    }

    @DeleteMapping("/{marketId}")
    public ResponseEntity<Void> deleteMarket(@PathVariable Long marketId){
        marketOwnerService.deleteMarket(marketId);
        return ResponseEntity.ok().build();
    }

}
