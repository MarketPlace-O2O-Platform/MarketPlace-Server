package com.appcenter.marketplace.domain.image;

import com.appcenter.marketplace.domain.market.Market;
import com.appcenter.marketplace.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "image")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Image extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn( name = "market_id", nullable = false)
    private Market market;

    @Column(nullable = false)
    private Integer order;

    @Builder
    public Image(String name, Market market, Integer order) {
        this.name = name;
        this.market = market;
        this.order = order;
    }
}
