package com.example.rest.global.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RsData {
    private String code;
    private String msg;
    private Object data;

    public RsData(String code, String msg) {
        this(code, msg, null);
    }
}
