package com.appcenter.marketplace.domain.requestMarket;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "request_market")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RequestMarket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private Long count;

    @Builder
    public RequestMarket(String name, String address, Long count) {
        this.name = name;
        this.address = address;
        this.count = count;
    }
}
