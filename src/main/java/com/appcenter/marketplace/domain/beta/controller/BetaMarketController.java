package com.appcenter.marketplace.domain.beta.controller;


import com.appcenter.marketplace.domain.beta.dto.req.BetaMarketReq;
import com.appcenter.marketplace.domain.beta.dto.res.BetaMarketRes;
import com.appcenter.marketplace.domain.beta.service.BetaMarketService;
import com.appcenter.marketplace.global.common.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.appcenter.marketplace.global.common.StatusCode.MARKET_CREATE;
import static com.appcenter.marketplace.global.common.StatusCode.MARKET_UPDATE;

@Tag(name = "[베타 버전 매장]", description = "[베타] 매장 관리")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/beta/markets")
public class BetaMarketController {
    private final BetaMarketService betaMarketService;

    @Operation(summary = "베타 매장 생성", description = "1개의 베타 버전 매장을 생성합니다. 생성 시 모든 유저에게 쿠폰을 제공합니다. <br>" +
            "이미지를 가져오려면 /image/{image.name}을 fetch하면 됩니다.")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CommonResponse<BetaMarketRes>> createMarket(
            @RequestPart(value = "jsonData") @Valid BetaMarketReq betaMarketReq,
            @RequestPart(value = "image") MultipartFile multipartFile){
        return ResponseEntity
                .status(MARKET_CREATE.getStatus())
                .body(CommonResponse.from(MARKET_CREATE.getMessage()
                        ,betaMarketService.createBetaMarket(betaMarketReq,multipartFile)));
    }

    @Operation(summary = "베타 매장 정보 수정", description = "1개의 베타 버전 매장의 정보를 수정합니다. 수정 시 모든 유저에게 쿠폰을 재발송합니다. <br>" +
            "이미지를 가져오려면 /image/{image.name}을 fetch하면 됩니다.")
    @PutMapping(path = "/{betaMarketId}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CommonResponse<BetaMarketRes>> updateMarket(
            @PathVariable Long betaMarketId,
            @RequestPart(value = "jsonData") @Valid BetaMarketReq betaMarketReq,
            @RequestPart(value = "image") MultipartFile multipartFile){
        return ResponseEntity
                .status(MARKET_UPDATE.getStatus())
                .body(CommonResponse.from(MARKET_UPDATE.getMessage()
                        ,betaMarketService.updateBetaMarket(betaMarketId, betaMarketReq,multipartFile)));
    }
}
