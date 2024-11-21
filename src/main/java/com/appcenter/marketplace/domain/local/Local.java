package com.appcenter.marketplace.domain.local;


import com.appcenter.marketplace.domain.metro.Metro;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;



@Table(name = "local_government")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Local {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "metropolitan_government_id",nullable = false)
    private Metro metro;
}

