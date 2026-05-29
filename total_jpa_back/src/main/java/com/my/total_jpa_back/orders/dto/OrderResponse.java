package com.my.total_jpa_back.orders.dto;


import com.my.total_jpa_back.common.entity.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class OrderResponse {
    private Long orderId;
    private String productName;
    private Integer price;
    private String userName;
    private OrderStatus status;
    private LocalDateTime orderCreatedAt;
}
