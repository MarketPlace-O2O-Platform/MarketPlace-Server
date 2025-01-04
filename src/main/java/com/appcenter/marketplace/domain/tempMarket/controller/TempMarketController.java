package com.appcenter.marketplace.domain.tempMarket.controller;

import com.appcenter.marketplace.domain.tempMarket.dto.req.TempMarketReq;
import com.appcenter.marketplace.domain.tempMarket.dto.res.TempMarketHiddenRes;
import com.appcenter.marketplace.domain.tempMarket.dto.res.TempMarketDetailRes;
import com.appcenter.marketplace.domain.tempMarket.dto.res.TempMarketPageRes;
import com.appcenter.marketplace.domain.tempMarket.dto.res.TempMarketRes;
import com.appcenter.marketplace.domain.tempMarket.service.TempMarketService;
import com.appcenter.marketplace.global.common.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.appcenter.marketplace.global.common.StatusCode.*;

@Tag(name = "[관리자 임시 매장]", description = "[공감탭 임시매장] 임시 매장 관리 ")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tempMarkets")
public class TempMarketController {
    private final TempMarketService tempMarketService;

    @Operation(summary = "임시 매장 생성", description = "공감 탭에 생성할 매장을 생성합니다.")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CommonResponse<TempMarketDetailRes>> createTempMarket(
            @RequestPart(value = "file")MultipartFile multipartFile,
            @RequestPart(value = "jsonData") @Valid TempMarketReq tempMarketReq) {

        return ResponseEntity.status(MARKET_CREATE.getStatus())
                .body(CommonResponse.from(MARKET_CREATE.getMessage(), tempMarketService.create(tempMarketReq, multipartFile)));
    }

    @Operation(summary = "매장 정보 수정", description = "매장에 대한 기본 정보를 수정합니다. <br>" +
            "기본적인 정보 수정이 가능하고, 이미지는 1개 (썸네일용)이므로 해당 API를 통해 수정이 가능합니다.")
    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CommonResponse<TempMarketDetailRes>> updateTempMarket(
            @RequestParam(name="marketId") Long marketId,
            @RequestPart(value ="file", required = false) MultipartFile multipartFile,
            @RequestPart(value = "jsonData") TempMarketReq tempMarketReq
    ){
        return ResponseEntity.status(MARKET_UPDATE.getStatus())
                .body(CommonResponse.from(MARKET_UPDATE.getMessage(), tempMarketService.updateMarket(marketId, tempMarketReq, multipartFile)));
    }

    @Operation(summary = "매장 공개(숨김) 처리", description = "매장을 공개(숨김)처리가 가능합니다." )
    @PutMapping("/hidden/{tempMarketId}")
    public ResponseEntity<CommonResponse<TempMarketHiddenRes>> toggleTempMarket(
            @PathVariable Long tempMarketId
    ){
        return ResponseEntity.status(MARKET_HIDDEN.getStatus())
                .body(CommonResponse.from(MARKET_HIDDEN.getMessage(), tempMarketService.toggleHidden(tempMarketId)));
    }

    @Operation(summary = "매장 기본 조회 (전체/카테고리별 조회)", description = "기본 실행은 매장 전체 조회힙니다. <br>" +
            "'category'를 입력하면 카테고리별 조회가 가능합니다. <br>" +
            "'lastPageIndex를 입력하면 페이징 처리를 통해 무한 스크롤 방식의 매장 조회가 가능힙니다. <br>" +
            "'count'는 기본 10개씩 입니다. API를 조회할 시 불러와지는 매장의 갯수입니다.")
    @GetMapping
    public ResponseEntity<CommonResponse<TempMarketPageRes<TempMarketRes>>> getTempMarket(
            @RequestParam Long memberId,
            @RequestParam(required = false, name = "lastPageIndex") Long tempMarketId,
            @RequestParam(required = false, name = "category") String category,
            @RequestParam(defaultValue = "10", name = "count") Integer size
    ){
        return ResponseEntity.status(MARKET_FOUND.getStatus())
                .body(CommonResponse.from(MARKET_FOUND.getMessage(), tempMarketService.getMarketList(memberId, tempMarketId, size, category)));
    }

    @Operation(summary = "달성 임박 매장 조회", description = "달성이 임박한 매장을 공감순으로 조회합니다. <br>" +
            "20개가 각 매장의 목표 공감수 입니다. 이중 14개(70%) 이상이 될 경우에만 조회가 가능합니다. 또한, 공감수(cheerCount)가 20개 이상이면, '매장 컨텍 중'으로 보여주시면 됩니다. <br>" +
            "'lastPageIndex', 'lastCheerCount'를 입력하면 페이징 처리를 통해 무한 스크롤 방식의 매장 조회가 가능합니다. <br>" +
            "'count'는 기본 10개씩입니다. API를 조회할 시 불러와지는 매장의 갯수입니다.")
    @GetMapping("/cheer")
    public ResponseEntity<CommonResponse<TempMarketPageRes<TempMarketRes>>> getTempMarketPage(
            @RequestParam Long memberId,
            @RequestParam(required = false, name = "lastPageIndex") Long tempMarketId,
            @RequestParam(required = false, name = "lastCheerCount") Long cheerCount,
            @RequestParam(defaultValue = "10", name = "count") Integer size
    ){
        return ResponseEntity.status(MARKET_FOUND.getStatus())
                .body(CommonResponse.from(MARKET_FOUND.getMessage(), tempMarketService.getUpcomingNearMarketList(memberId, tempMarketId, cheerCount, size)));
    }

    @Operation(summary = "매장 삭제", description = "매장을 삭제합니다.")
    @DeleteMapping("/{tempMarketId}")
    public ResponseEntity<CommonResponse<Object>> deleteMarket(
            @PathVariable Long tempMarketId
    ){
        tempMarketService.deleteMarket(tempMarketId);
        return ResponseEntity.ok(CommonResponse.from(MARKET_DELETE.getMessage()));
    }
}
