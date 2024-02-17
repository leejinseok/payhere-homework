package com.payhere.homework.api.application.domain.auth;

import com.payhere.homework.api.application.exception.UnauthorizedException;
import com.payhere.homework.api.presentation.domain.auth.dto.LoginRequest;
import com.payhere.homework.api.presentation.domain.auth.dto.SignUpRequest;
import com.payhere.homework.core.db.domain.owner.ShopOwner;
import com.payhere.homework.core.db.domain.owner.ShopOwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.payhere.homework.api.application.constants.ExceptionConstants.LOGIN_FAILED;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final ShopOwnerRepository shopOwnerRepository;

    @Transactional
    public ShopOwner signUp(final SignUpRequest request) {

        ShopOwner shopOwner = ShopOwner.builder()
                .phoneNumber(request.getPhoneNumber())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        return shopOwnerRepository.save(shopOwner);
    }

    public ShopOwner login(final LoginRequest request) {
        String phoneNumber = request.getPhoneNumber();

        Optional<ShopOwner> optional = shopOwnerRepository.findByPhoneNumber(phoneNumber);
        if (optional.isEmpty()) {
            throw new UnauthorizedException(LOGIN_FAILED);
        }

        ShopOwner shopOwner = optional.get();
        if (!passwordEncoder.matches(request.getPassword(), shopOwner.getPassword())) {
            throw new UnauthorizedException(LOGIN_FAILED);
        }

        return shopOwner;
    }

}
