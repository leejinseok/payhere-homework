package com.payhere.homework.api.application.domain.auth;

import com.payhere.homework.api.presentation.auth.dto.SignUpRequest;
import com.payhere.homework.core.db.domain.owner.ShopOwner;
import com.payhere.homework.core.db.domain.owner.ShopOwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public ShopOwner login() {
        return null;
    }

}
