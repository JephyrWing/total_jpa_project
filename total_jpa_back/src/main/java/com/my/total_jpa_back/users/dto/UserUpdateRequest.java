package com.my.total_jpa_back.users.dto;

import com.my.total_jpa_back.common.entity.Gender;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateRequest {
    //Id는 path variable로 받을 거임
    private String name;
    private Gender gender;
    private String email;
    private String likeColor;
}
