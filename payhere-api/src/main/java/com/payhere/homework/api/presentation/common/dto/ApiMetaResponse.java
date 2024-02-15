package com.payhere.homework.api.presentation.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class ApiMetaResponse {

    private int code;
    private Object message;

}
