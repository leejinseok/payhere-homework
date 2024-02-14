package com.payhere.homework.api.application.domain.auth;

import com.payhere.homework.api.presentation.auth.dto.SignUpRequest;
import com.payhere.homework.core.db.domain.Phone;
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

        Phone phone = Phone.builder()
                .number1(request.getPhoneNumber1())
                .number2(request.getPhoneNumber2())
                .number3(request.getPhoneNumber3())
                .build();

        ShopOwner shopOwner = ShopOwner.builder()
                .shopOwnerPhone(phone)
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        return shopOwnerRepository.save(shopOwner);
    }

}
