package com.payhere.homework.api.presentation.auth;

import com.payhere.homework.api.application.config.jwt.JwtProvider;
import com.payhere.homework.api.application.domain.auth.AuthService;
import com.payhere.homework.api.presentation.auth.dto.*;
import com.payhere.homework.api.presentation.owner.dto.ShopOwnerResponse;
import com.payhere.homework.core.db.domain.owner.ShopOwner;
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

    @PostMapping("/shop-owner/sign-up")
    public ResponseEntity<SignUpResponse> signUp(@RequestBody @Valid final SignUpRequest request) {
        ShopOwner shopOwner = authService.signUp(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(SignUpResponse.create(shopOwner));
    }

    @PostMapping("/shop-owner/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid final LoginRequest request) {
        ShopOwner shopOwner = authService.login(request);

        String accessToken = jwtProvider.createToken(shopOwner);
        String refreshToken = jwtProvider.createRefreshToken(shopOwner);
        TokenResponse tokenResponse = TokenResponse.of(accessToken, refreshToken);

        ShopOwnerResponse memberResponse = ShopOwnerResponse.create(shopOwner);
        LoginResponse loginResponse = LoginResponse.of(memberResponse, tokenResponse);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(loginResponse);
    }


}
