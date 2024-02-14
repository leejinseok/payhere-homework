package com.payhere.homework.api.application.domain.auth;

import com.payhere.homework.api.application.config.ApiDbConfig;
import com.payhere.homework.api.presentation.auth.dto.SignUpRequest;
import com.payhere.homework.core.db.domain.owner.ShopOwner;
import com.payhere.homework.core.db.domain.owner.ShopOwnerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

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

}