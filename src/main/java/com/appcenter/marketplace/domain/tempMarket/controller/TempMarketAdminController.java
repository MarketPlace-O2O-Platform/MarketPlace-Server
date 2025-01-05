package com.appcenter.marketplace.domain.tempMarket.controller;

import com.appcenter.marketplace.domain.tempMarket.dto.req.TempMarketReq;
import com.appcenter.marketplace.domain.tempMarket.dto.res.TempMarketDetailRes;
import com.appcenter.marketplace.domain.tempMarket.dto.res.TempMarketHiddenRes;
import com.appcenter.marketplace.domain.tempMarket.service.TempMarketAdminService;
import com.appcenter.marketplace.global.common.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.appcenter.marketplace.global.common.StatusCode.*;
import static com.appcenter.marketplace.global.common.StatusCode.MARKET_HIDDEN;

@Tag(name ="[관리자 공감매장]", description = "[관리자] 관리자 공감매장 관리")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/tempMarkets")
public class TempMarketAdminController {
    private final TempMarketAdminService tempMarketAdminService;

    @Operation(summary = "임시 매장 생성", description = "공감 탭에 생성할 매장을 생성합니다.")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CommonResponse<TempMarketDetailRes>> createTempMarket(
            @RequestPart(value = "file") MultipartFile multipartFile,
            @RequestPart(value = "jsonData") @Valid TempMarketReq tempMarketReq) {

        return ResponseEntity.status(MARKET_CREATE.getStatus())
                .body(CommonResponse.from(MARKET_CREATE.getMessage(), tempMarketAdminService.createMarket(tempMarketReq, multipartFile)));
    }

    @Operation(summary = "매장 정보 수정", description = "매장에 대한 기본 정보를 수정합니다. <br>" +
            "기본적인 정보 수정이 가능하고, 이미지는 1개 (썸네일용)이므로 해당 API를 통해 수정이 가능합니다.")
    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CommonResponse<TempMarketDetailRes>> updateTempMarket(
            @RequestParam(name="marketId") Long marketId,
            @RequestPart(value ="file", required = false) MultipartFile multipartFile,
            @RequestPart(value = "jsonData") @Valid TempMarketReq tempMarketReq
    ){
        return ResponseEntity.status(MARKET_UPDATE.getStatus())
                .body(CommonResponse.from(MARKET_UPDATE.getMessage(), tempMarketAdminService.updateMarket(marketId, tempMarketReq, multipartFile)));
    }

    @Operation(summary = "매장 정보 전체 조회", description = "매장 전체를 조회합니다.")
    @GetMapping
    public ResponseEntity<CommonResponse<List<TempMarketDetailRes>>> getAllTempMarket() {
        return ResponseEntity.ok(CommonResponse.from(MARKET_FOUND.getMessage(), tempMarketAdminService.getMarketList()));
    }

    @Operation(summary = "매장 공개(숨김) 처리", description = "매장을 공개(숨김)처리가 가능합니다." )
    @PutMapping("/hidden/{tempMarketId}")
    public ResponseEntity<CommonResponse<TempMarketHiddenRes>> toggleTempMarket(
            @PathVariable Long tempMarketId
    ){
        return ResponseEntity.status(MARKET_HIDDEN.getStatus())
                .body(CommonResponse.from(MARKET_HIDDEN.getMessage(), tempMarketAdminService.toggleHidden(tempMarketId)));
    }

    @Operation(summary = "매장 삭제", description = "매장을 삭제합니다.")
    @DeleteMapping("/{tempMarketId}")
    public ResponseEntity<CommonResponse<Object>> deleteMarket(
            @PathVariable Long tempMarketId
    ){
        tempMarketAdminService.deleteMarket(tempMarketId);
        return ResponseEntity.ok(CommonResponse.from(MARKET_DELETE.getMessage()));
    }

}
