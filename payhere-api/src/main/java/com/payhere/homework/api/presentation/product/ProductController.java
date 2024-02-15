package com.payhere.homework.api.presentation.product;

import com.payhere.homework.api.application.config.jwt.MemberContext;
import com.payhere.homework.api.application.domain.product.ProductService;
import com.payhere.homework.api.presentation.common.dto.ApiResponse;
import com.payhere.homework.api.presentation.product.dto.ProductRequest;
import com.payhere.homework.api.presentation.product.dto.ProductResponse;
import com.payhere.homework.core.db.domain.product.Product;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/{productId}")
    public ResponseEntity<ApiResponse<ProductResponse>> getProduct(
            @PathVariable final Long productId,
            @AuthenticationPrincipal final MemberContext memberContext
    ) {
        Product product = productService.findOne(memberContext.getId(), productId);
        ApiResponse<ProductResponse> body = new ApiResponse<>(
                HttpStatus.OK,
                ProductResponse.from(product)
        );

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(body);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ProductResponse>> saveProduct(
            @RequestBody @Valid final ProductRequest request,
            @AuthenticationPrincipal final MemberContext memberContext
    ) {
        Product save = productService.save(memberContext.getId(), request);
        ApiResponse<ProductResponse> body = new ApiResponse<>(
                HttpStatus.CREATED,
                ProductResponse.from(save)
        );

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(body);
    }

    @PatchMapping("/{productId}")
    public ResponseEntity<ApiResponse<ProductResponse>> updateProduct(
            @PathVariable final Long productId,
            @RequestBody final ProductRequest request,
            @AuthenticationPrincipal final MemberContext memberContext
    ) {
        Product updated = productService.update(memberContext.getId(), productId, request);
        ApiResponse<ProductResponse> body = new ApiResponse<>(
                HttpStatus.OK,
                ProductResponse.from(updated)
        );

        return ResponseEntity.status(HttpStatus.OK)
                .body(body);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(
            @PathVariable final Long productId,
            @AuthenticationPrincipal final MemberContext memberContext
    ) {
        productService.delete(memberContext.getId(), productId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }


}
