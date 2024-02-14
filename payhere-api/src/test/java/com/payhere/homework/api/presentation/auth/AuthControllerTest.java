package com.payhere.homework.api.presentation.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.payhere.homework.api.application.config.ApiSecurityConfig;
import com.payhere.homework.api.application.config.jwt.JwtConfig;
import com.payhere.homework.api.application.domain.auth.AuthService;
import com.payhere.homework.api.presentation.auth.dto.SignUpRequest;
import com.payhere.homework.api.presentation.common.dto.ValidationErrorResponse;
import com.payhere.homework.core.db.domain.owner.ShopOwner;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static com.payhere.homework.api.application.constants.ValidationConstants.PHONE_NUMBER_NOT_VALID;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = {AuthController.class})
@Import({ApiSecurityConfig.class, JwtConfig.class})
class AuthControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    @MockBean
    AuthService authService;

    @Test
    void 회원가입_실패_잘못된_폰번호() throws Exception {
        SignUpRequest request = SignUpRequest.of("잘못된폰번호테스트", "password");
        byte[] content = objectMapper.writeValueAsBytes(request);

        ValidationErrorResponse errorResponse = new ValidationErrorResponse();
        errorResponse.getErrors().add(PHONE_NUMBER_NOT_VALID);
        String jsonErrorResponse = objectMapper.writeValueAsString(errorResponse);

        mockMvc.perform(
                        post("/api/v1/auth/shop-owner/sign-up")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().json(jsonErrorResponse));
    }

    @Test
    void 회원가입() throws Exception {
        SignUpRequest request = SignUpRequest.of("01011112222", "password");
        byte[] content = objectMapper.writeValueAsBytes(request);

        ShopOwner saved = ShopOwner.builder()
                .id(1L)
                .phoneNumber(request.getPhoneNumber())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        when(authService.signUp(any())).thenReturn(saved);

        mockMvc.perform(
                        post("/api/v1/auth/shop-owner/sign-up")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(saved.getId()))
                .andExpect(jsonPath("$.phoneNumber").value(saved.getPhoneNumber()));
    }
}