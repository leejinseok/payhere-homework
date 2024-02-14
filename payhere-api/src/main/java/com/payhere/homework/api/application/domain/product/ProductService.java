package com.payhere.homework.api.application.domain.product;

import com.payhere.homework.api.application.exception.NotFoundException;
import com.payhere.homework.api.presentation.product.dto.ProductSaveRequest;
import com.payhere.homework.core.db.domain.product.Product;
import com.payhere.homework.core.db.domain.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.payhere.homework.api.application.constants.ExceptionConstants.NOT_EXIST_PRODUCT;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public Product save(final ProductSaveRequest request) {
        Product newProduct = Product.builder()
                .name(request.getName())
                .expiryDate(request.getExpiryDate())
                .barcode(request.getBarcode())
                .price(request.getPrice())
                .category(request.getCategory())
                .costPrice(request.getCostPrice())
                .description(request.getDescription())
                .size(request.getSize())
                .build();

        return productRepository.save(newProduct);
    }

    @Transactional
    public Product update(final Long productId, final ProductSaveRequest request) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new NotFoundException(NOT_EXIST_PRODUCT));

        String barcode = request.getBarcode();
        return null;
    }

}
