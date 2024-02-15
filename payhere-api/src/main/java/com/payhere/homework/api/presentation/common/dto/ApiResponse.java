package com.payhere.homework.api.presentation.common.dto;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiResponse<T> {

    private ApiMetaResponse meta;
    private T data;

    public ApiResponse(final int code, final Object message, final T data) {
        this.meta = ApiMetaResponse.of(code, message);
        this.data = data;
    }

    public ApiResponse(final HttpStatus httpStatus, final T data) {
        this.meta = ApiMetaResponse.of(httpStatus.value(), httpStatus.name());
        this.data = data;
    }

}
