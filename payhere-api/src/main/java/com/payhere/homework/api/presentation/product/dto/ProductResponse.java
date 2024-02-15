package com.payhere.homework.api.presentation.product.dto;

import com.payhere.homework.api.presentation.owner.dto.ShopOwnerResponse;
import com.payhere.homework.core.db.audit.AuditingDomain;
import com.payhere.homework.core.db.domain.product.Product;
import com.payhere.homework.core.db.domain.product.ProductCategory;
import com.payhere.homework.core.db.domain.product.ProductSize;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@AllArgsConstructor(staticName = "of")
public class ProductResponse extends AuditingDomain {

    private Long id;
    private String name;
    private ProductCategory category;
    private BigDecimal price;
    private BigDecimal costPrice;
    private String description;
    private String barcode;
    private LocalDate expiryDate;
    private ProductSize size;
    private ShopOwnerResponse shopOwner;

    public static ProductResponse from(final Product product) {
        ProductResponse productResponse = ProductResponse.of(
                product.getId(),
                product.getName(),
                product.getCategory(),
                product.getPrice(),
                product.getCostPrice(),
                product.getDescription(),
                product.getBarcode(),
                product.getExpiryDate(),
                product.getSize(),
                ShopOwnerResponse.from(product.getShopOwner())
        );

        productResponse.setCreatedAt(product.getCreatedAt());
        productResponse.setLastModifiedAt(product.getLastModifiedAt());
        return productResponse;
    }

}
