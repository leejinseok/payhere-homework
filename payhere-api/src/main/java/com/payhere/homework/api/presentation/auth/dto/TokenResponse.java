package com.payhere.homework.api.presentation.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class TokenResponse {

    @Schema(example = "accessToken")
    private String accessToken;

    @Schema(example = "refreshToken")
    private String refreshToken;

}
