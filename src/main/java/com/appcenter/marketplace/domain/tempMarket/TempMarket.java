package com.appcenter.marketplace.domain.tempMarket;


import com.appcenter.marketplace.domain.category.Category;
import com.appcenter.marketplace.domain.cheer.Cheer;
import com.appcenter.marketplace.domain.tempMarket.dto.req.TempMarketReq;
import com.appcenter.marketplace.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Table(name = "temp_market")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TempMarket extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = true)
    private String description;

    @Column(nullable = true)
    private String thumbnail;

    @Column(nullable = true)
    private String address;

    @Column(name = "cheer_count", nullable = false)
    private Integer cheerCount;

    @Column(name = "is_hidden", nullable = false)
    private Boolean isHidden;

    @Builder
    public TempMarket(Category category, String name, String description, String thumbnail, String address, Integer cheerCount, Boolean isHidden) {
        this.category = category;
        this.name = name;
        this.description = description;
        this.thumbnail = thumbnail;
        this.address = address;
        this.cheerCount = cheerCount;
        this.isHidden = isHidden;
    }

    public void updateMarket(TempMarketReq marketReq, Category category){
        this.category = category;
        this.name = marketReq.getMarketName();
        this.description = marketReq.getDescription();
        this.address = marketReq.getAddress();
    }

    public void updateThumbnail(String thumbnail){
        this.thumbnail = thumbnail;
    }

    public void toggleHidden() {
        this.isHidden = !this.isHidden;
    }

    public void increaseCheerCount() {
        this.cheerCount++;
    }

    public void decreaseCheerCount() {
        this.cheerCount--;

        if(this.cheerCount < 0){
            this.cheerCount = 0;
        }
    }
}
