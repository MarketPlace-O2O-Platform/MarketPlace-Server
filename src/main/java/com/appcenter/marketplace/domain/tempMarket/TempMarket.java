package com.appcenter.marketplace.domain.tempMarket;


import com.appcenter.marketplace.domain.category.Category;
import com.appcenter.marketplace.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private Long cheerCount;

    @Column(name = "is_hidden", nullable = false)
    private Boolean isHidden;

    @Builder
    public TempMarket(Category category, String name, String description, String thumbnail, String address, Long cheerCount, Boolean isHidden) {
        this.category = category;
        this.name = name;
        this.description = description;
        this.thumbnail = thumbnail;
        this.address = address;
        this.cheerCount = cheerCount;
        this.isHidden = isHidden;
    }
}
