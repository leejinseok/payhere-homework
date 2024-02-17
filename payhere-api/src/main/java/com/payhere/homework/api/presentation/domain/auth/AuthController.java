package com.payhere.homework.api.presentation.domain.auth;

import com.payhere.homework.api.application.config.jwt.JwtProvider;
import com.payhere.homework.api.application.domain.auth.AuthService;
import com.payhere.homework.api.presentation.common.dto.ApiResponse;
import com.payhere.homework.api.presentation.domain.auth.dto.*;
import com.payhere.homework.api.presentation.domain.dto.ShopOwnerResponse;
import com.payhere.homework.core.db.domain.owner.ShopOwner;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Auth (인증)")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtProvider jwtProvider;

    @Operation(description = "회원가입", summary = "회원가입")
    @PostMapping("/shop-owner/sign-up")
    public ResponseEntity<ApiResponse<SignUpResponse>> signUp(@RequestBody @Valid final SignUpRequest request) {
        ShopOwner shopOwner = authService.signUp(request);

        ApiResponse<SignUpResponse> response = new ApiResponse<>(
                HttpStatus.CREATED,
                SignUpResponse.create(shopOwner)
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @Operation(description = "로그인", summary = "로그인")
    @PostMapping("/shop-owner/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody @Valid final LoginRequest request) {
        ShopOwner shopOwner = authService.login(request);

        String accessToken = jwtProvider.createToken(shopOwner);
        String refreshToken = jwtProvider.createRefreshToken(shopOwner);
        TokenResponse tokenResponse = TokenResponse.of(accessToken, refreshToken);

        ShopOwnerResponse memberResponse = ShopOwnerResponse.from(shopOwner);
        LoginResponse loginResponse = LoginResponse.of(memberResponse, tokenResponse);

        ApiResponse<LoginResponse> body = new ApiResponse<>(
                HttpStatus.OK,
                loginResponse
        );

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(body);
    }


}
