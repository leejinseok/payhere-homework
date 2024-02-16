package com.payhere.homework.api.presentation.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static com.payhere.homework.api.application.constants.ValidationConstants.*;

@Getter
@AllArgsConstructor(staticName = "of")
public class LoginRequest {

    @Schema(example = "01011112222")
    @NotEmpty(message = PHONE_NUMBER_NOT_BE_NULL)
    @Pattern(regexp = "^\\d{3}\\d{3,4}\\d{4}$", message = PHONE_NUMBER_NOT_VALID)
    private String phoneNumber;

    @Schema(example = "your password")
    @NotEmpty(message = PASSWORD_MUST_NOT_NULL)
    private String password;

}
