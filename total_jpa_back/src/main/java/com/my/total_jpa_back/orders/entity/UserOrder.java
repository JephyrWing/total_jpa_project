package com.my.total_jpa_back.orders.entity;

import com.my.total_jpa_back.common.entity.BaseEntity;
import com.my.total_jpa_back.common.entity.OrderStatus;
import com.my.total_jpa_back.users.entity.Users;
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

//    @Column(name = "user_id") // Users table의 FK
//    private Long userId;

    // Foreign Key인 Users 객체 자체를 포함
    @ManyToOne(fetch = FetchType.LAZY) // 필요할 때(호출할 때)만 로딩, on delete나 on update 설정은 괄호 안에 cascade= 으로 설정
    @JoinColumn(name = "user_id")
    private Users user;

    @Column(name = "product_name")
    private String productName;

    private Integer price; // 나중에 QueryDSL 조건 때문에 integer로 선언

    @Enumerated(EnumType.STRING)
    private OrderStatus status;


}
