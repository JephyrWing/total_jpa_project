package com.my.total_jpa_back.repository;

import com.my.total_jpa_back.entity.Gender;
import com.my.total_jpa_back.entity.Users;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
        for(Users user : users) {
            log.info("name = {}, gender = {}", user.getName(), user.getGender());
        }
    }

    @Test
    @DisplayName("포함 조건 조회")
    void 포함_조회() {
        List<Users> users = userRepository.findByNameContaining("김");
        for(Users user : users) {
            log.info("name = {}", user.getName());
        }
    }

    @Test
    @DisplayName("findByLikeColor")
    void findByLikeColor() {
        List<Users> users = userRepository.findByLikeColor("blue");
        for(Users user : users) {
            log.info("name = {}, color = {}", user.getName(), user.getLikeColor());
        }
    }


    @Test
    @DisplayName("findByLikeColorAndGender")
    void findByLikeColorAndGender() {
        List<Users> users = userRepository.findByLikeColorAndGender("blue", Gender.Male);
        for(Users user : users) {
            log.info("name = {}, color = {}, gender = {}", user.getName(), user.getLikeColor(), user.getGender());
        }
    }
}