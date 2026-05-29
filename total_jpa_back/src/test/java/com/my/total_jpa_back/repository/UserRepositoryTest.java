package com.my.total_jpa_back.repository;

import com.my.total_jpa_back.common.entity.Gender;
import com.my.total_jpa_back.orders.entity.UserOrder;
import com.my.total_jpa_back.users.entity.Users;
import com.my.total_jpa_back.users.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("회원 전체 조회")
    void findAllTest() {
        // when
        List<Users> users = userRepository.findAll();
        // then
        assertThat(users).size().isEqualTo(500);
    }

    @Test
    @DisplayName("성별 조건 조회")
    void 성별_조회() { // Test 메서드는 한글로 선언해도 된다
        List<Users> users = userRepository.findByGender(Gender.Male);
        for (Users user : users) {
            log.info("name = {}, gender = {}", user.getName(), user.getGender());
        }
    }

    @Test
    @DisplayName("포함 조건 조회")
    void 포함_조회() {
        List<Users> users = userRepository.findByNameContaining("김");
        for (Users user : users) {
            log.info("name = {}", user.getName());
        }
    }

    @Test
    @DisplayName("findByLikeColor")
    void findByLikeColor() {
        List<Users> users = userRepository.findByLikeColor("blue");
        for (Users user : users) {
            log.info("name = {}, color = {}", user.getName(), user.getLikeColor());
        }
    }


    @Test
    @DisplayName("findByLikeColorAndGender")
    void findByLikeColorAndGender() {
        List<Users> users = userRepository.findByLikeColorAndGender("blue", Gender.Male);
        for (Users user : users) {
            log.info("name = {}, color = {}, gender = {}", user.getName(), user.getLikeColor(), user.getGender());
        }
    }

    //=========================================================================================================================
    // Sort

    @Test
    @DisplayName("회원 이름 오름차순 정렬")
    void orderByNameAscTest() {
        // 정렬 기계를 하나 세팅
        Sort sort = Sort
                .by("name")
                .ascending();
        // 전체 검색할 때 sort 기계를 삽입해서 정렬되도록 처리
        List<Users> users = userRepository.findAll(sort);
        users.stream().limit(5).forEach(x -> log.info("name : {}", x.getName()));
    }

    @Test
    @DisplayName("최근 가입 회원 출력")
    void orderByCreatedAtDesc() {
        Sort sort = Sort
                .by("createdAt")
                .descending();
        List<Users> users = userRepository.findAll(sort);
        users.stream().limit(10).forEach(x -> log.info("user_id : {}, name : {}, created_at : {}", x.getId(), x.getName(), x.getCreatedAt()));
    }

    // 색상 오름차순, 같은 색상은 이름 내림차순, 상위 100개
    @Test
    @DisplayName("색상 오름차순, 이름 내림차순")
    void multiSortTest() {
        Sort sort = Sort
                .by("likeColor")
                .ascending()
                .and(Sort
                        .by("name")
                        .descending()
                );
        List<Users> users = userRepository.findAll(sort);
        users.stream().limit(100).forEach(x -> log.info("name : {}, color : {}", x.getName(), x.getLikeColor()));
    }

    // Paging 처리에 사용되는 인터페이스 : Pageable
    // impl class : PageRequest.of

    // 전체 회원 자료를 10개씩 묶음
    @Test
    @DisplayName("회원목록 페이징 테스트")
    void pagingTest() {
        Pageable pageable = PageRequest.of(49, 10);
        Page<Users> result = userRepository.findAll(pageable);
        // Page 객체는 내가 요청한 자료 + 기타 정보(전체 페이지 수, 현재 페이지 위치 등등)
        // Page가 준 내용 중 리스트만 뽑아서 리스트로 저장
        List<Users> users = result.getContent();
        // 전체 행 수
        log.info("전체 행 수 : " + result.getTotalElements());
        // 전체 페이지 수
        log.info("전체 페이지 수 : " + result.getTotalPages());
        // 현재 페이지
        log.info("현재 페이지 : " + result.getNumber());
        // 다음 페이지 여부
        log.info("다음 페이지 여부 : " + result.hasNext());
        // 이전 페이지 여부
        log.info("이전 페이지 여부 : " + result.hasPrevious());
        users.forEach(x -> log.info("All : {}", x));
    }

    // 최근 가입한 회원정보 10번째 페이지
    // 페이지당 30개 출력
    @Test
    @DisplayName("최근 가입한 회원정보")
    void pagingAndSort() {
        Sort sort = Sort.by("createdAt").descending();
        Pageable pageable = PageRequest.of(9, 30, sort);
        Page<Users> result = userRepository.findAll(pageable);
        List<Users> users = result.getContent();
        // 전체 행 수
        log.info("전체 행 수 : " + result.getTotalElements());
        // 전체 페이지 수
        log.info("전체 페이지 수 : " + result.getTotalPages());
        // 현재 페이지
        log.info("현재 페이지 : " + result.getNumber());
        // 다음 페이지 여부
        log.info("다음 페이지 여부 : " + result.hasNext());
        // 이전 페이지 여부
        log.info("이전 페이지 여부 : " + result.hasPrevious());
        users.forEach(x -> log.info("All : {}", x));
    }

    // Slice : 무한 스크롤 용으로 자료가 필요할 때
    // Page 보다 가볍다....고함(?)
    @Test
    @DisplayName("회원을 Slice로 조회")
    void sliceTest() {
        Pageable pageable = PageRequest.of(0, 20,
                Sort.by("createdAt").descending());
        Slice<Users> result = userRepository.findAll(pageable);
        List<Users> users = result.getContent();

        // 전체 행 수
        // log.info("전체 행 수 : " + result.getTotalElements());
        // 전체 페이지 수
        // log.info("전체 페이지 수 : " + result.getSize());
        // 현재 페이지
        log.info("현재 페이지 : " + result.getNumber());
        // 다음 페이지 여부
        log.info("다음 페이지 여부 : " + result.hasNext());
        // 이전 페이지 여부
        log.info("이전 페이지 여부 : " + result.hasPrevious());
        users.forEach(x -> log.info("All : {}", x));
    }

//    @Test
//    @DisplayName("회원정보 조회 후 주문정보 찾아보기")
//    @Transactional
//    void findUserAndOrderInfoTest() {
//        Users user = userRepository.findById(1L)
//                .orElseThrow();
//        log.info("이름 : {}", user.getName());
//        user.getOrders().forEach(x ->
//                log.info("제품명 : {}, 가격 : {}", x.getProductName(), x.getPrice()));
//    }

//    @Test
//    @DisplayName("N+1 문제 확인")
//    @Transactional
//    void nPlusOneTest(){
//        List<Users> user = userRepository.findAll();
//        user.forEach(x->
//                {
//                    log.info("이름 : {}", x.getName());
//                    x.getOrders().forEach(y ->
//                            log.info("주문 번호 : {}, 제품명 : {}, 가격 : {}", y.getId(), y.getProductName(), y.getPrice())
//                    );
//                }
//        );
//    }

//    @Test
//    @DisplayName("JPQL로 가져오기")
//    @Transactional
//    void joinTest() {
//        List<Users> user = userRepository.findAllWithOrders();
//        user.forEach(x->
//                {
//                    log.info("이름 : {}", x.getName());
//                    x.getOrders().forEach(y ->
//                            log.info("주문 번호 : {}, 제품명 : {}, 가격 : {}", y.getId(), y.getProductName(), y.getPrice())
//                    );
//                }
//        );
//    }
}