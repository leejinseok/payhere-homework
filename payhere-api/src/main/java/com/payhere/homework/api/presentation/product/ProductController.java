package com.payhere.homework.api.presentation.product;

import com.payhere.homework.api.application.domain.product.ProductService;
import com.payhere.homework.api.presentation.product.dto.ProductSaveRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public void saveProduct(@RequestBody @Valid ProductSaveRequest request) {
    }

}
