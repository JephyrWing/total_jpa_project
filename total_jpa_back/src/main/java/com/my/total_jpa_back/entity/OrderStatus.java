package com.my.total_jpa_back.entity;

import lombok.Getter;

@Getter
public enum OrderStatus {
    READY,
    SHIPPING,
    COMPLETE,
    CANCEL
}
