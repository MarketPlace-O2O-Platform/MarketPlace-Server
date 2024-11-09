package com.appcenter.marketplace.domain.category;

import com.appcenter.marketplace.global.common.Major;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "category",
        indexes = {
                @Index(name = "idx_category_major", columnList = "major") // major 컬럼에 인덱스 추가
        })
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Major major;


//    @Column(nullable = false)
//    @Enumerated(EnumType.STRING)
//    private String minor;
}
