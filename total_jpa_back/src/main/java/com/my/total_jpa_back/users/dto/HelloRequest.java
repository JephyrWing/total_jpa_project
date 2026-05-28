package com.my.total_jpa_back.users.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//api 요청 시 전달되는 매개변수들을 받는 DTO
//PostMapping 요청용
public class HelloRequest {
    private String name;
    private int age;
}
