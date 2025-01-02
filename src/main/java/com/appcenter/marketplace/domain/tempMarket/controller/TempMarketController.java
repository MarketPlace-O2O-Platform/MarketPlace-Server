package com.appcenter.marketplace.domain.tempMarket.controller;

import com.appcenter.marketplace.domain.tempMarket.dto.req.TempMarketReq;
import com.appcenter.marketplace.domain.tempMarket.dto.res.TempMarketRes;
import com.appcenter.marketplace.domain.tempMarket.service.TempMarketService;
import com.appcenter.marketplace.global.common.CommonResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.appcenter.marketplace.global.common.StatusCode.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tempMarkets")
public class TempMarketController {
    private final TempMarketService tempMarketService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CommonResponse<TempMarketRes>> createTempMarket(
            @RequestPart(value = "file")MultipartFile multipartFile,
            @RequestPart(value = "jsonData") @Valid TempMarketReq tempMarketReq) {

        return ResponseEntity.status(MARKET_CREATE.getStatus())
                .body(CommonResponse.from(MARKET_CREATE.getMessage(), tempMarketService.create(tempMarketReq, multipartFile)));
    }

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CommonResponse<TempMarketRes>> updateTempMarket(
            @RequestParam(name="marketId") Long marketId,
            @RequestPart(value ="file", required = false) MultipartFile multipartFile,
            @RequestPart(value = "jsonData") TempMarketReq tempMarketReq
    ){
        return ResponseEntity.status(MARKET_UPDATE.getStatus())
                .body(CommonResponse.from(MARKET_UPDATE.getMessage(), tempMarketService.updateMarket(marketId, tempMarketReq, multipartFile)));
    }
}
