package com.payhere.homework.api.presentation.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class ApiMetaResponse {

    @Schema(example = "200")
    private int code;

    @Schema(example = "OK")
    private Object message;

}
