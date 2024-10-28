package com.appcenter.marketplace.domain.market.controller;

import com.appcenter.marketplace.domain.market.dto.req.MarketCreateReqDto;
import com.appcenter.marketplace.domain.market.dto.req.MarketUpdateReqDto;
import com.appcenter.marketplace.domain.market.dto.res.MarketResDto;
import com.appcenter.marketplace.domain.market.service.MarketOwnerService;
import com.appcenter.marketplace.global.common.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.appcenter.marketplace.global.common.StatusCode.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/owners/markets")
public class MarketOwnerController {
    private final MarketOwnerService marketOwnerService;

    @Operation(summary = "사장님 매장 생성", description = "사장님이 1개의 매장을 생성합니다.")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CommonResponse<MarketResDto>> createMarket(
            @RequestPart(value = "jsonData") @Valid MarketCreateReqDto marketCreateReqDto,
            @RequestPart(value = "files") List<MultipartFile> multipartFileList) throws IOException {
        return ResponseEntity
                .status(MARKET_CREATE.getStatus())
                .body(CommonResponse.from(MARKET_CREATE.getMessage(),marketOwnerService.createMarket(marketCreateReqDto,multipartFileList)));
    }

    @Operation(summary = "사장님 매장 정보 수정", description = "사장님이 생성한 매장 정보를 수정합니다.")
    @PutMapping("/{marketId}")
    public ResponseEntity<CommonResponse<MarketResDto>> updateMarket(@RequestBody @Valid MarketUpdateReqDto marketUpdateReqDto,
                                                     @PathVariable Long marketId) {
        return ResponseEntity.ok(CommonResponse.from(MARKET_UPDATE.getMessage(),marketOwnerService.updateMarket(marketUpdateReqDto,marketId)));
    }

    @Operation(summary = "사장님 매장 조회", description = "사장님이 생성한 매장 정보를 조회합니다.")
    @GetMapping("/{marketId}")
    public ResponseEntity<CommonResponse<MarketResDto>> getMarket(@PathVariable Long marketId){
        return ResponseEntity.ok(CommonResponse.from(MARKET_FOUND.getMessage(),marketOwnerService.getMarket(marketId)));
    }

    @Operation(summary = "사장님 매장 삭제", description = "사장님이 생성한 매장을 삭제합니다. ")
    @DeleteMapping("/{marketId}")
    public ResponseEntity<CommonResponse<Object>> deleteMarket(@PathVariable Long marketId){
        marketOwnerService.deleteMarket(marketId);
        return ResponseEntity.ok(CommonResponse.from(MARKET_DELETE.getMessage()));
    }

}
