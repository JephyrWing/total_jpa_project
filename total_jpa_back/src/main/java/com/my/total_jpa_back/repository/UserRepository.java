package com.my.total_jpa_back.repository;

import com.my.total_jpa_back.entity.Gender;
import com.my.total_jpa_back.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<Users, Long> {
    // 1. 성별로 조회
    List<Users> findByGender(Gender gender); // 이렇게 까지만 쓰면 JPA가 알아서 완성해준다.

    // 2. 이름에 특정 문자를 포함하는 검색 : Containing
    List<Users> findByNameContaining(String keyword);

    // 3. 좋아하는 색 검색
    List<Users> findByLikeColor(String color); // 언더바 있는 컬럼은 camel case로 변환

    // 4. 색상과 성별로 검색
    List<Users> findByLikeColorAndGender(String color, Gender gender);


}
