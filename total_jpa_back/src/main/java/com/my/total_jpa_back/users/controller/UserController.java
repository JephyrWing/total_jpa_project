package com.my.total_jpa_back.users.controller;

import com.my.total_jpa_back.common.entity.Gender;
import com.my.total_jpa_back.common.exception.UserNotFoundException;
import com.my.total_jpa_back.users.dto.*;
import com.my.total_jpa_back.users.entity.Users;
import com.my.total_jpa_back.users.repository.UserRepository;
import com.my.total_jpa_back.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Restful한 API를 제공할 때 사용하는 어노테이션
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final UserRepository userRepository;
    private final UserService userService;

    // 전체 리스트를 요청
    // 이건 테스트 용이라 entity를 그대로 리스트로 만들어 보내지만
    // 실전에선 dto 변환 후 리스트로 묶어 보내야 함
    @GetMapping("/users")
    public List<Users> findAll() {
        return userRepository.findAll();
    }

    @GetMapping("/gender/{gender}")
    public List<Users> findByGender(@PathVariable("gender") Gender gender) {
        return userRepository.findByGender(gender);
    }

    @GetMapping("/name")
    public List<Users> findByName(@RequestParam("keyword") String keyword) {
        return userRepository.findByNameContaining(keyword);
    }

    @GetMapping("/color")
    public List<Users> findByColor(@RequestParam("color") String color) {
        return userRepository.findByLikeColor(color);
    }

    @GetMapping("/color-gender")
    public List<Users> findByColorAndGender(@RequestParam("color") String color,
                                            @RequestParam("gender") Gender gender) {
        return userRepository.findByLikeColorAndGender(color, gender);
    }

    @GetMapping("/email")
    public List<Users> findByEmailDomain(@RequestParam("domain") String domain) {
        return userRepository.findByEmailContaining(domain);
    }

    // 이름 : 오름차순, 생성일에 내림차순
    @GetMapping("/sort")
    public List<Users> findAllSort() {
        Sort sort = Sort.by("name")
                .ascending()
                .and(Sort.by("createdAt")
                        .descending());
        return userRepository.findAll(sort);
    }

    @GetMapping("/page")
    public Page<Users> findAllPage(@RequestParam(name = "page", defaultValue = "0") int page,
                                   @RequestParam(name = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return userRepository.findAll(pageable);
    }

    @GetMapping("/slice")
    public Slice<Users> findAllSlice(@RequestParam(name = "page", defaultValue = "0") int page,
                                     @RequestParam(name = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return userRepository.findAll(pageable);
    }

    // RequestBody 테스트
    @PostMapping("/test")
    // RequestBody : json으로 들어온 데이터를 받음
    public HelloResponse test(@RequestBody HelloRequest request) {
        return HelloResponse.builder()
                .message("안녕하세요" + request.getName())
                .age(request.getAge())
                .build();
    }

    // 예외처리 테스트
    @GetMapping("/users/{id}")
    public Users findById(@PathVariable Long id){
        return userRepository.findById(id).orElseThrow(()-> new UserNotFoundException());
    }

    // 새로운 User 추가하기 API
    @PostMapping("/users")
    public UserResponse create(@RequestBody UserCreateRequest request) {
        return userService.create(request);
    }

    // User Update API
    // 수정 대상(ID)은 PathVariable, 값은 RequestBody로 받아서 수정
    @PutMapping("/users/{id}")
    public UserResponse update(
            @PathVariable Long id,
            @RequestBody UserUpdateRequest request
            ) {
        return userService.update(id, request);
    }

    // User ID로 삭제처리 API 만들기
    @DeleteMapping("/users/{id}")
    public String delete(@PathVariable Long id){
        userService.delete(id);
        return "삭제 완료";
    }

}
