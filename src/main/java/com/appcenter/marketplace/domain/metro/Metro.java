package com.appcenter.marketplace.domain.metro;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Table(name = "metropolitan_government")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Metro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;


}
