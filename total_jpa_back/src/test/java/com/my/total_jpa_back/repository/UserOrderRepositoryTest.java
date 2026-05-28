package com.my.total_jpa_back.repository;

import com.my.total_jpa_back.common.entity.OrderStatus;
import com.my.total_jpa_back.orders.entity.UserOrder;
import com.my.total_jpa_back.orders.repository.UserOrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
class UserOrderRepositoryTest {
    @Autowired
    UserOrderRepository userOrderRepository;

    @Test
    @DisplayName("전체 주문 조회")
    void findAll() {
        List<UserOrder> orders = userOrderRepository.findAll();
        assertThat(orders.size()).isEqualTo(5000);
    }

    @Test
    @DisplayName("주문 상태로 조회")
    void findByStatus() {
        List<UserOrder> orders = userOrderRepository.findByStatus(OrderStatus.COMPLETE);
        for(UserOrder order : orders) {
            log.info("productname = {}, status = {}", order.getProductName() , order.getStatus());
        }
    }

    @Test
    @DisplayName("상품명 포함 검색")
    void findByProductNameContaining() {
        List<UserOrder> orders = userOrderRepository.findByProductNameContaining("Dunk");
        for(UserOrder order : orders) {
            log.info("productname = {}", order.getProductName());
        }
    }

    @Test
    @DisplayName("특정 가격 이상 조회")
    void findByPriceGreaterThanEqual() {
        List<UserOrder> orders = userOrderRepository.findByPriceGreaterThanEqual(300000);
        for(UserOrder order : orders) {
            log.info("productname = {}, price = {}", order.getProductName() , order.getPrice());
        }
    }

    @Test
    @DisplayName("특정 회원 주문 조회")
    void findByUserId() {
        List<UserOrder> orders = userOrderRepository.findByUserId(10L);
        for(UserOrder order : orders) {
            log.info("productname = {}, userid = {}", order.getProductName() , order.getUserId());
        }
    }

    @Test
    @DisplayName("회원 + 주문상태 조회")
    void findByUserIdAndStatus() {
        List<UserOrder> orders = userOrderRepository.findByUserIdAndStatus(10L, OrderStatus.COMPLETE);
        for(UserOrder order : orders) {
            log.info("productname = {}, userid = {}, status = {}", order.getProductName() , order.getUserId(), order.getStatus());
        }
    }

    @Test
    @DisplayName("가격 범위 조회")
    void findByPriceBetween() {
        List<UserOrder> orders = userOrderRepository.findByPriceBetween(290000, 300000);
        for(UserOrder order : orders) {
            log.info("productname = {}, price = {}", order.getProductName() , order.getPrice());
        }
    }

    @Test
    @DisplayName("가격 높은 순 조회")
    void findAllByOrderByDesc() {
        List<UserOrder> orders = userOrderRepository.findAllByOrderByPriceDesc();
        for(UserOrder order : orders) {
            log.info("productname = {}, price = {}", order.getProductName() , order.getPrice());
        }
    }

    @Test
    @DisplayName("최신 주문 5개 조회")
    void findTop5ByCreatedAtOrderByDesc() {
        List<UserOrder> orders = userOrderRepository.findTop5ByOrderByCreatedAtDesc();
        for(UserOrder order : orders) {
            log.info("productname = {}, date = {}", order.getProductName() , order.getCreatedAt());
        }
    }

    @Test
    @DisplayName("상태 여러 개 조회")
    void findByStatusIn() {
        List<OrderStatus> statusList = new ArrayList<>(
                List.of(OrderStatus.READY, OrderStatus.SHIPPING)
        );
//        statusList.add(OrderStatus.SHIPPING);
//        statusList.add(OrderStatus.READY);
        List<UserOrder> orders = userOrderRepository.findByStatusIn(statusList);
        for(UserOrder order : orders) {
            log.info("productname = {}, status = {}", order.getProductName() , order.getStatus());
        }
    }

    //===============================================================================================================
    // Sort

    @Test
    @DisplayName("주문 상태 오름차순, 제품명 내림차순, 주문일 내림차순")
    void multiSortTest() {
        Sort sort = Sort
                .by("status")
                .ascending()
                .and(Sort
                        .by("productName")
                        .descending()
                        .and(Sort.by("createdAt").descending())
                );
        List<UserOrder> orders = userOrderRepository.findAll(sort);
        orders.stream().limit(500).forEach(x->log.info("status: {}, productName : {}, createdAt : {}", x.getStatus(), x.getProductName(), x.getCreatedAt()));
    }
}