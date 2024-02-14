package com.payhere.homework.api.application.domain.product;

import com.payhere.homework.api.application.exception.DuplicateException;
import com.payhere.homework.api.application.exception.NotFoundException;
import com.payhere.homework.api.application.exception.UnauthorizedException;
import com.payhere.homework.api.presentation.product.dto.ProductRequest;
import com.payhere.homework.core.db.domain.owner.ShopOwner;
import com.payhere.homework.core.db.domain.owner.ShopOwnerRepository;
import com.payhere.homework.core.db.domain.product.Product;
import com.payhere.homework.core.db.domain.product.ProductCategory;
import com.payhere.homework.core.db.domain.product.ProductRepository;
import com.payhere.homework.core.db.domain.product.ProductSize;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.payhere.homework.api.application.constants.ExceptionConstants.*;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ShopOwnerRepository shopOwnerRepository;

    @Transactional
    public Product save(final Long shopOwnerId, final ProductRequest request) {
        ShopOwner shopOwner = shopOwnerRepository.findById(shopOwnerId).orElseThrow(() -> {
            throw new NotFoundException(NOT_EXIST_SHOP_OWNER);
        });

        Product newProduct = Product.builder()
                .name(request.getName())
                .expiryDate(request.getExpiryDate())
                .barcode(request.getBarcode())
                .price(request.getPrice())
                .category(request.getCategory())
                .costPrice(request.getCostPrice())
                .description(request.getDescription())
                .size(request.getSize())
                .shopOwner(shopOwner)
                .build();

        if (barcodeDuplicateCheck(request.getBarcode())) {
            throw new DuplicateException(ALREADY_EXIST_PRODUCT);
        }

        return productRepository.save(newProduct);
    }

    public Product findById(final Long productId) {
        return productRepository.findById(productId).orElseThrow(() -> new NotFoundException(NOT_EXIST_PRODUCT));
    }

    @Transactional
    public Product update(final Long shopOwnerId, final Long productId, final ProductRequest request) {
        Product product = findById(productId);

        if (!product.doesShopOwnerOwnThisProduct(shopOwnerId)) {
            throw new UnauthorizedException(DO_NOT_HAVE_UPDATE_PRODUCT_PERMISSION);
        }

        String name = request.getName();
        if (name != null) {
            product.updateName(name);
        }

        String barcode = request.getBarcode();
        if (barcode != null) {
            // 변경하고자 하는 바코드가 현재 상품의 바코드와 다르고
            if (!barcode.equals(product.getBarcode())) {
                // 변경하고자 하는 바코드가 중복된 바코드가 아닌경우에만 변경
                if (!barcodeDuplicateCheck(barcode)) {
                    product.updateBarcode(barcode);
                }
            }
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

    public void delete(final Long shopOwnerId, final Long productId) {
        Product product = findById(productId);
        if (!product.doesShopOwnerOwnThisProduct(shopOwnerId)) {
            throw new UnauthorizedException(DO_NOT_HAVE_DELETE_PRODUCT_PERMISSION);
        }
    }

    private boolean barcodeDuplicateCheck(final String barcode) {
        return productRepository.findByBarcode(barcode).isPresent();
    }

}
