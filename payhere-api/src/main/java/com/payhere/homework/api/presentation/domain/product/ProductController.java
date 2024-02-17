package com.payhere.homework.api.presentation.domain.product;

import com.payhere.homework.api.application.config.jwt.MemberContext;
import com.payhere.homework.api.application.domain.product.ProductService;
import com.payhere.homework.api.presentation.common.dto.ApiResponse;
import com.payhere.homework.api.presentation.common.dto.PageResponse;
import com.payhere.homework.api.presentation.domain.product.dto.ProductRequest;
import com.payhere.homework.api.presentation.domain.product.dto.ProductResponse;
import com.payhere.homework.core.db.domain.product.Product;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Product (상품)")
@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @Operation(description = "상품조회", summary = "페이지")
    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<ProductResponse>>> getProducts(
            @RequestParam(required = false) final String productName,
            @Parameter(name = "page", example = "0") final int page,
            @Parameter(name = "size", example = "10") final int size,
            @AuthenticationPrincipal final MemberContext memberContext
    ) {

        Page<Product> productPage = productService.findPage(memberContext.getId(), productName, PageRequest.of(page, size));
        Page<ProductResponse> productResponsePage = productPage.map(ProductResponse::from);

        ApiResponse<PageResponse<ProductResponse>> body = new ApiResponse<>(
                HttpStatus.OK,
                new PageResponse<>(productResponsePage)
        );

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(body);
    }

    @Operation(description = "상품조회", summary = "단건")
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

    @Operation(description = "상품등록", summary = "상품등록")
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

    @Operation(description = "상품수정", summary = "상품수정")
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

    @Operation(description = "상품삭제", summary = "상품삭제")
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
