package com.payhere.homework.api.application.domain.product;

import com.payhere.homework.api.application.exception.DuplicateException;
import com.payhere.homework.api.application.exception.NotFoundException;
import com.payhere.homework.api.presentation.product.dto.ProductRequest;
import com.payhere.homework.core.db.domain.product.Product;
import com.payhere.homework.core.db.domain.product.ProductCategory;
import com.payhere.homework.core.db.domain.product.ProductRepository;
import com.payhere.homework.core.db.domain.product.ProductSize;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.payhere.homework.api.application.constants.ExceptionConstants.ALREADY_EXIST_PRODUCT;
import static com.payhere.homework.api.application.constants.ExceptionConstants.NOT_EXIST_PRODUCT;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public Product save(final ProductRequest request) {
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

        productRepository.findByBarcode(request.getBarcode()).ifPresent(product -> {
            throw new DuplicateException(ALREADY_EXIST_PRODUCT);
        });

        return productRepository.save(newProduct);
    }

    @Transactional
    public Product update(final Long productId, final ProductRequest request) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new NotFoundException(NOT_EXIST_PRODUCT));

        String name = request.getName();
        if (name != null) {
            product.updateName(name);
        }

        String barcode = request.getBarcode();
        if (barcode != null) {
            product.updateBarcode(barcode);
        }

        ProductSize size = request.getSize();
        if (size != null) {
            product.updateSize(size);
        }

        ProductCategory category = request.getCategory();
        if (category != null) {
            product.updateCategory(category);
        }

        BigDecimal price = request.getPrice();
        if (price != null) {
            product.updatePrice(price);
        }

        BigDecimal costPrice = request.getCostPrice();
        if (costPrice != null) {
            product.updateCostPrice(costPrice);
        }

        String description = request.getDescription();
        if (description != null) {
            product.updateDescription(description);
        }

        LocalDate expiryDate = request.getExpiryDate();
        if (expiryDate != null) {
            product.updateExpiryDate(expiryDate);
        }

        return product;
    }

}
