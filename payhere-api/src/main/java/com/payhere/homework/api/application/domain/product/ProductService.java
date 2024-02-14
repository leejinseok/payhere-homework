package com.payhere.homework.api.application.domain.product;

import com.payhere.homework.api.presentation.product.dto.ProductSaveRequest;
import com.payhere.homework.core.db.domain.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public void save(final ProductSaveRequest request) {
    }

}
