package com.my.total_jpa_back.orders.service;

import com.my.total_jpa_back.common.entity.OrderStatus;
import com.my.total_jpa_back.orders.dto.OrderResponse;
import com.my.total_jpa_back.orders.repository.UserOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserOrderService {
    private final UserOrderRepository userOrderRepository;

    public List<OrderResponse> findByStatusWithUsers(OrderStatus status) {
        return userOrderRepository.findByStatusWithUsers(status);
    }

    public List<OrderResponse> findByMultiConditionWithUsers(OrderStatus status, Integer price, String name) {
        return userOrderRepository.findByMultiConditionWithUsers(status, price, name);
    }

    public List<OrderResponse> findByUserIdWithUsers(Long id) {
        return userOrderRepository.findByUserIdWithUsers(id);
    }
}
