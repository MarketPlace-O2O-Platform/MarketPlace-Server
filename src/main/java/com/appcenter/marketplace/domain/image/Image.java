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
    private Integer sequence;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn( name = "market_id", nullable = false)
    private Market market;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    @Builder
    public Image(Integer sequence, String name, Market market, Boolean isDeleted) {
        this.sequence = sequence;
        this.name = name;
        this.market = market;
        this.isDeleted = isDeleted;
    }

    public void updateSequence(Integer sequence){ this.sequence = sequence;}

    public void deleteImage(){ isDeleted = true;}
}
