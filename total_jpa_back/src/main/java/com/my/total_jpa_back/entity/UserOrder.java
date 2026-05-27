package com.my.total_jpa_back.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "user_order")
public class UserOrder extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id") // Users table의 FK
    private Long userId;

    @Column(name = "product_name")
    private String productName;

    private Integer price; // 나중에 QueryDSL 조건 때문에 integer로 선언

    @Enumerated(EnumType.STRING)
    private OrderStatus status;
}
