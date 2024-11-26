package com.appcenter.marketplace.domain.market.controller;

import com.appcenter.marketplace.domain.market.dto.req.MarketCreateReq;
import com.appcenter.marketplace.domain.market.dto.req.MarketImageUpdateReq;
import com.appcenter.marketplace.domain.market.dto.req.MarketUpdateReq;
import com.appcenter.marketplace.domain.market.dto.res.MarketDetailsRes;
import com.appcenter.marketplace.domain.market.service.MarketOwnerService;
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

@Tag(name = "[사장님 매장]", description = "[사장님] 사장님 매장 관리")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/owners/markets")
public class MarketOwnerController {
    private final MarketOwnerService marketOwnerService;

    @Operation(summary = "사장님 매장 생성", description = "사장님이 1개의 매장을 생성합니다. <br>" +
            "이미지를 가져오려면 /image/{image.name}을 fetch하면 됩니다.")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CommonResponse<MarketDetailsRes>> createMarket(
            @RequestPart(value = "jsonData") @Valid MarketCreateReq marketCreateReq,
            @RequestPart(value = "images") List<MultipartFile> multipartFileList){
        return ResponseEntity
                .status(MARKET_CREATE.getStatus())
                .body(CommonResponse.from(MARKET_CREATE.getMessage()
                        ,marketOwnerService.createMarket(marketCreateReq,multipartFileList)));
    }

    @Operation(summary = "사장님 매장 정보 수정", description = "사장님이 생성한 매장 정보를 수정합니다.")
    @PutMapping("/{marketId}")
    public ResponseEntity<CommonResponse<MarketDetailsRes>> updateMarket(
            @RequestBody @Valid MarketUpdateReq marketUpdateReq, @PathVariable Long marketId) {
        return ResponseEntity
                .ok(CommonResponse.from(MARKET_UPDATE.getMessage()
                        ,marketOwnerService.updateMarket(marketId, marketUpdateReq)));
    }

    @Operation(summary = "사장님 매장 이미지 수정", description = "매장의 이미지를 추가,삭제 및 순서 변경을 합니다. <br>" +
            "jsonData는 삭제할 이미지 id 리스트, 순서 변경할 Map<이미지id,순서order>, 추가할 이미지의 순서 리스트입니다. <br>" +
            "images는 추가할 이미지 리스트입니다. 기존 이미지는 안주셔도 됩니다. <br>" +
            "예시) 추가할 이미지의 순서 리스트가 [2,4] 이면, 이미지 파일은 2개여야하고, 첫번 째 사진은 순서 2가 부여됩니다. 두번 째 사진은 4가 부여됩니다.")
    @PutMapping(value = "/images",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CommonResponse<MarketDetailsRes>> updateMarket(
            @RequestPart(value = "jsonData") MarketImageUpdateReq marketImageUpdateReq,
            @RequestPart(value = "images", required = false) List<MultipartFile> multipartFileList,
            @RequestParam(name = "marketId") Long marketId){
        return ResponseEntity
                .ok(CommonResponse.from(MARKET_IMAGE_UPDATE.getMessage()
                        ,marketOwnerService.updateMarketImage(marketId, marketImageUpdateReq,multipartFileList)));
    }

    @Operation(summary = "사장님 매장 삭제", description = "사장님이 생성한 매장을 삭제합니다. ")
    @DeleteMapping("/{marketId}")
    public ResponseEntity<CommonResponse<Object>> deleteMarket(@PathVariable Long marketId){
        marketOwnerService.deleteMarket(marketId);
        return ResponseEntity
                .ok(CommonResponse.from(MARKET_DELETE.getMessage()));
    }
}
