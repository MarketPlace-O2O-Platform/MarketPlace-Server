package com.appcenter.marketplace.domain.requestMarket.dto.req;

import com.appcenter.marketplace.domain.requestMarket.RequestMarket;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RequestMarketReq {

    @NotBlank(message = "매장명은 필수입력값입니다.")
    private String name;

    @NotBlank(message = "주소값은 필수입력값입니다.")
    private String address;

    private Integer count;

    public RequestMarket toEntity(){
        return RequestMarket.builder()
                .name(name)
                .address(address)
                .count(1)
                .build();
    }
}
