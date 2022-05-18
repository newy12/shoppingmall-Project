package com.example.toyproject.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.annotation.RegEx;

@RequiredArgsConstructor
@Getter
@AllArgsConstructor
public enum Role {
    GUEST("ROLE_GUEST","손님"),
    USER("ROLE_USER","일반 사용자"),
    MEMBER("ROLE_MEMBER","멤버"),
    ADMIN("ROLE_ADMIN","관리자");

    private final String key;
    private final String title;

    private String value;
}
