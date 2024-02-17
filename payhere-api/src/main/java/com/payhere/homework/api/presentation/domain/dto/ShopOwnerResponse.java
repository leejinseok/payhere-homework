package com.payhere.homework.api.presentation.domain.dto;

import com.payhere.homework.api.presentation.common.dto.AuditingResponse;
import com.payhere.homework.core.db.domain.owner.ShopOwner;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class ShopOwnerResponse extends AuditingResponse {

    @Schema(example = "1")
    private Long id;

    @Schema(example = "01011112222")
    private String phoneNumber;

    public static ShopOwnerResponse from(final ShopOwner shopOwner) {
        ShopOwnerResponse response = new ShopOwnerResponse();
        response.id = shopOwner.getId();
        response.phoneNumber = shopOwner.getPhoneNumber();
        response.setCreatedAt(shopOwner.getCreatedAt());
        response.setLastModifiedAt(shopOwner.getLastModifiedAt());
        return response;
    }

}
