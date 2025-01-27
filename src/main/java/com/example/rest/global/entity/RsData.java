package com.example.rest.global.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RsData {
    private String code;
    private String msg;
    private Long id;

    public RsData(String code, String msg) {
        this(code, msg, null);
    }
}
