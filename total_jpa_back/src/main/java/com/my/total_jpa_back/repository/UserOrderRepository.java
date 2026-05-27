package com.my.total_jpa_back.repository;

import com.my.total_jpa_back.entity.OrderStatus;
import com.my.total_jpa_back.entity.UserOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UserOrderRepository extends JpaRepository<UserOrder, Long> {
    // 주문 상태로 조회
    List<UserOrder> findByStatus(OrderStatus status);

    // 상품명 포함 검색
    List<UserOrder> findByProductNameContaining(String name);

    // 특정 가격 이상 조회( >= : GreaterThanEqual, <= : LessThanEqual )
    List<UserOrder> findByPriceGreaterThanEqual(Integer price);

    // 특정 회원 주문 조회
    List<UserOrder> findByUserId(Long userid);

    // 회원 + 주문 상태 조회
    List<UserOrder> findByUserIdAndStatus(Long userid, OrderStatus status);

    // 가격 범위 조회(Between)
    List<UserOrder> findByPriceBetween(Integer min, Integer max);

    // 가격 높은 순 조회(OrderBy)
    List<UserOrder> findAllByOrderByPriceDesc();

    //최신 주문 5개 조회(findTop5By) + 내림차순
    List<UserOrder> findTop5ByOrderByCreatedAtDesc();

    // 상태 여러 개 조회(In) in 안에 조건이 여러개 들어갈 수 있으니 parameter를 리스트로 줘야 받아먹는다.
    List<UserOrder> findByStatusIn(List<OrderStatus> statuslist);

}
