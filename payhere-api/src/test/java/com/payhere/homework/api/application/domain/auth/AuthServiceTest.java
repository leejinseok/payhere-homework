package com.payhere.homework.api.application.domain.auth;

import com.payhere.homework.api.application.config.ApiDbConfig;
import com.payhere.homework.api.application.exception.UnauthorizedException;
import com.payhere.homework.api.presentation.auth.dto.LoginRequest;
import com.payhere.homework.api.presentation.auth.dto.SignUpRequest;
import com.payhere.homework.core.db.domain.owner.ShopOwner;
import com.payhere.homework.core.db.domain.owner.ShopOwnerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles({"test"})
@DataJpaTest
@Import({ApiDbConfig.class})
class AuthServiceTest {

    @Autowired
    ShopOwnerRepository shopOwnerRepository;

    @Test
    void 회원가입() {
        AuthService authService = new AuthService(new BCryptPasswordEncoder(), shopOwnerRepository);
        SignUpRequest request = SignUpRequest.of("01011112222", "password");
        ShopOwner shopOwner = authService.signUp(request);

        assertThat(shopOwner.getPhoneNumber()).isEqualTo(request.getPhoneNumber());
        assertThat(shopOwner.getPassword()).isNotEqualTo(request.getPassword());
    }

    @Test
    void 로그인실패() {
        AuthService authService = new AuthService(new BCryptPasswordEncoder(), shopOwnerRepository);
        SignUpRequest signUpRequest = SignUpRequest.of("01011112222", "password");
        authService.signUp(signUpRequest);

        LoginRequest loginRequest = LoginRequest.of(signUpRequest.getPhoneNumber(), signUpRequest.getPassword() + "a");
        assertThrows(UnauthorizedException.class, () -> {
            authService.login(loginRequest);
        });
    }

}