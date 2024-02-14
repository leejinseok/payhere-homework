package com.payhere.homework.api.presentation.auth.dto;

import com.payhere.homework.api.presentation.owner.dto.ShopOwnerResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class LoginResponse {

    private ShopOwnerResponse shopOwner;
    private TokenResponse token;

}
