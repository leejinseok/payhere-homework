package com.payhere.homework.api.presentation.auth.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

import static com.payhere.homework.api.application.constants.ValidationConstants.*;

@Getter
public class SignUpRequest {

    @Pattern(regexp = "^\\d{3}$", message = PHONE_NUMBER_ONLY_3_DIGIT_ALLOWED)
    private String phoneNumber1;

    @Pattern(regexp = "^\\d{4}$", message = PHONE_NUMBER_ONLY_4_DIGIT_ALLOWED)
    private String phoneNumber2;

    @Pattern(regexp = "^\\d{4}$", message = PHONE_NUMBER_ONLY_4_DIGIT_ALLOWED)
    private String phoneNumber3;

    @NotNull(message = PASSWORD_MUST_NOT_NULL)
    private String password;

}
