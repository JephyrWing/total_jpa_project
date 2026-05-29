package com.my.total_jpa_back.orders.dto;

import com.my.total_jpa_back.common.entity.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderSearchRequest {
    private OrderStatus status;
    private Integer price;
    private String keyword;
}
