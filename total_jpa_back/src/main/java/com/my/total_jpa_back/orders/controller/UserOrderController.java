package com.my.total_jpa_back.orders.controller;

import com.my.total_jpa_back.common.entity.OrderStatus;
import com.my.total_jpa_back.orders.dto.OrderResponse;
import com.my.total_jpa_back.orders.dto.OrderSearchRequest;
import com.my.total_jpa_back.orders.service.UserOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserOrderController {
    private final UserOrderService userOrderService;

    @GetMapping("/status")
    public List<OrderResponse> statusResponse(@RequestParam OrderStatus status){
        return userOrderService.findByStatusWithUsers(status);
    }

    @PostMapping("/multi-search")
    public List<OrderResponse> multiConditionResponse(@RequestBody OrderSearchRequest dto){
        return userOrderService.findByMultiConditionWithUsers(dto.getStatus(), dto.getPrice(), dto.getKeyword());
    }

    @GetMapping("/users/{id}/orders")
    public List<OrderResponse> findByUserIdWithUsers(@PathVariable Long id) {
        return userOrderService.findByUserIdWithUsers(id);
    }
}
