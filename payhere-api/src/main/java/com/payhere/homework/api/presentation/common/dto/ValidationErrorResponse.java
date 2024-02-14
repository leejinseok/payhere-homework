package com.payhere.homework.api.presentation.common.dto;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ValidationErrorResponse {

    public static final String PHONE_NUMBER_NOT_VALID = "핸드폰 번호가 올바르지 않습니다";
    public static final String PASSWORD_MUST_NOT_NULL = "패스워드는 필수항목입니다";

    private final List<String> errors = new ArrayList<>();

}
