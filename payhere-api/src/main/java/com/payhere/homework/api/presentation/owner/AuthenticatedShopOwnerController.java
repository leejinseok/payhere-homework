package com.payhere.homework.api.presentation.owner;

import com.payhere.homework.api.application.domain.product.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Authenticated Shop Owner (인증된 사장님)")
@RestController
@RequestMapping("/api/v1/shop-owner/authenticated")
@RequiredArgsConstructor
public class AuthenticatedShopOwnerController {

    private final ProductService productService;

    @Operation(summary = "상품 추가")
    @PostMapping("/products")
    public ResponseEntity<?> addProduct() {
        return null;
    }


}
