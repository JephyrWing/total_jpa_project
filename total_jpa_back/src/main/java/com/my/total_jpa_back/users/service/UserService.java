package com.my.total_jpa_back.users.service;

import com.my.total_jpa_back.common.exception.UserNotFoundException;
import com.my.total_jpa_back.users.dto.UserCreateRequest;
import com.my.total_jpa_back.users.dto.UserResponse;
import com.my.total_jpa_back.users.dto.UserUpdateRequest;
import com.my.total_jpa_back.users.entity.Users;
import com.my.total_jpa_back.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional // 웬만하면 다 jakarta 꺼를 쓰지만 Transactional만은 spring 꺼를 쓴다.
    public UserResponse create(UserCreateRequest request) {
        // DTO -> Entity
        Users user = new Users();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setGender(request.getGender());
        user.setLikeColor(request.getLikeColor());
        //repository의 save() 메서드는 기본적으로 저장하고 난 다음 엔티티를 반환해준다.
        Users savedUser = userRepository.save(user);
        //Entity -> DTO 변환 후 리턴
        return UserResponse.from(savedUser);
    }

    @Transactional
    public UserResponse update(Long id, UserUpdateRequest request) {
        //먼저 수정한 id가 실재하는지 찾아본다.
        Users user = userRepository.findById(id).orElseThrow(()->new UserNotFoundException());
        user.setName(request.getName());
        user.setGender(request.getGender());
        user.setEmail(request.getEmail());
        user.setLikeColor(request.getLikeColor());
        // 저장하지 않아도 dirty checking에 의해 저장(된 것처럼 보이게 됨)
        return UserResponse.from(user);
    }

    @Transactional
    public void delete(Long id) {
        Users user = userRepository.findById(id).orElseThrow(()->new UserNotFoundException());
        userRepository.delete(user);
    }
}
