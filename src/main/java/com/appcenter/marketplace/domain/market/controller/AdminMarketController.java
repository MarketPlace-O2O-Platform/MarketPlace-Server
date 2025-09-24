package com.appcenter.marketplace.domain.market.controller;

import com.appcenter.marketplace.domain.market.dto.req.MarketImageUpdateReq;
import com.appcenter.marketplace.domain.market.dto.req.MarketReq;
import com.appcenter.marketplace.domain.market.dto.res.MarketDetailsRes;
import com.appcenter.marketplace.domain.market.dto.res.MarketPageRes;
import com.appcenter.marketplace.domain.market.dto.res.MarketRes;
import com.appcenter.marketplace.domain.market.service.AdminMarketService;
import com.appcenter.marketplace.global.common.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.appcenter.marketplace.global.common.StatusCode.*;

@Tag(name = "[관리자 매장]", description = "[관리자] 매장 전체 관리")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admins/markets")
public class AdminMarketController {
    private final AdminMarketService adminMarketService;

    @Operation(summary = "전체 매장 조회", description = "관리자가 전체 매장 목록을 조회합니다.")
    @GetMapping
    public ResponseEntity<CommonResponse<MarketPageRes<MarketRes>>> getAllMarkets(
            @Parameter(description = "페이지의 마지막 marketId")
            @RequestParam(required = false, name = "lastPageIndex") Long marketId,
            @RequestParam(required = false, name = "category") String major,
            @RequestParam(defaultValue = "10", name = "pageSize") Integer size) {
        return ResponseEntity
                .ok(CommonResponse.from(MARKET_FOUND.getMessage(),
                        adminMarketService.getAllMarkets(marketId, size, major)));
    }

    @Operation(summary = "매장 상세 조회", description = "관리자가 특정 매장의 상세 정보를 조회합니다.")
    @GetMapping("/{marketId}")
    public ResponseEntity<CommonResponse<MarketDetailsRes>> getMarket(@PathVariable Long marketId) {
        return ResponseEntity
                .ok(CommonResponse.from(MARKET_FOUND.getMessage(),
                        adminMarketService.getMarketDetails(marketId)));
    }

    @Operation(summary = "매장 생성", description = "관리자가 매장을 생성합니다.")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CommonResponse<MarketDetailsRes>> createMarket(
            @RequestPart(value = "jsonData") @Valid MarketReq marketReq,
            @RequestPart(value = "images") List<MultipartFile> multipartFileList) {
        return ResponseEntity
                .status(MARKET_CREATE.getStatus())
                .body(CommonResponse.from(MARKET_CREATE.getMessage(),
                        adminMarketService.createMarket(marketReq, multipartFileList)));
    }

    @Operation(summary = "매장 정보 수정", description = "관리자가 매장 정보를 수정합니다.")
    @PutMapping("/{marketId}")
    public ResponseEntity<CommonResponse<MarketDetailsRes>> updateMarket(
            @PathVariable Long marketId,
            @RequestBody @Valid MarketReq marketReq) {
        return ResponseEntity
                .ok(CommonResponse.from(MARKET_UPDATE.getMessage(),
                        adminMarketService.updateMarket(marketId, marketReq)));
    }

    @Operation(summary = "매장 이미지 수정", description = "관리자가 매장의 이미지를 추가,삭제 및 순서 변경을 합니다. <br>" +
            "jsonData는 삭제할 이미지 id 리스트, 순서 변경할 Map<이미지id,순서order>, 추가할 이미지의 순서 리스트입니다. <br>" +
            "images는 추가할 이미지 리스트입니다. 기존 이미지는 안주셔도 됩니다. <br>" +
            "예시) 추가할 이미지의 순서 리스트가 [2,4] 이면, 이미지 파일은 2개여야하고, 첫번 째 사진은 순서 2가 부여됩니다. 두번 째 사진은 4가 부여됩니다.")
    @PutMapping(value = "/{marketId}/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CommonResponse<MarketDetailsRes>> updateMarketImage(
            @PathVariable Long marketId,
            @RequestPart(value = "jsonData") MarketImageUpdateReq marketImageUpdateReq,
            @RequestPart(value = "images", required = false) List<MultipartFile> multipartFileList) {
        return ResponseEntity
                .ok(CommonResponse.from(MARKET_IMAGE_UPDATE.getMessage(),
                        adminMarketService.updateMarketImage(marketId, marketImageUpdateReq, multipartFileList)));
    }

    @Operation(summary = "매장 삭제", description = "관리자가 매장을 삭제합니다.")
    @DeleteMapping("/{marketId}")
    public ResponseEntity<CommonResponse<Object>> deleteMarket(@PathVariable Long marketId) {
        adminMarketService.deleteMarket(marketId);
        return ResponseEntity
                .ok(CommonResponse.from(MARKET_DELETE.getMessage()));
    }
}