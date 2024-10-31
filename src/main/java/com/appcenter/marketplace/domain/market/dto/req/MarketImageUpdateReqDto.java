package com.appcenter.marketplace.domain.market.dto.req;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor
public class MarketImageUpdateReqDto {
    private List<Long> deletedImageIds; // 삭제할 이미지 ID 리스트
    private List<Map<Long, Integer>> changedOrders; // 변경된 순서 (id와 Order를 Map으로 처리)
    private List<Integer> addedImageOrders; // 추가된 이미지의 순서 리스트 (순서 번호)

}
