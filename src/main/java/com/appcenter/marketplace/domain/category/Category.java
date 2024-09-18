package com.appcenter.marketplace.domain.category;

import com.appcenter.marketplace.global.common.BaseEntity;
import com.appcenter.marketplace.global.common.Major;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
