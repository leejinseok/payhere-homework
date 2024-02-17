package com.payhere.homework.api.presentation.domain.product.dto;

import com.payhere.homework.api.presentation.domain.dto.ShopOwnerResponse;
import com.payhere.homework.core.db.audit.AuditingDomain;
import com.payhere.homework.core.db.domain.product.Product;
import com.payhere.homework.core.db.domain.product.ProductCategory;
import com.payhere.homework.core.db.domain.product.ProductSize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@AllArgsConstructor(staticName = "of")
public class ProductResponse extends AuditingDomain {

    @Schema(example = "1")
    private Long id;

    @Schema(example = "에스프레소")
    private String name;

    @Schema(example = "COFFEE")
    private ProductCategory category;

    @Schema(example = "1500")
    private BigDecimal price;

    @Schema(example = "1000")
    private BigDecimal costPrice;

    @Schema(example = "향이 좋은 에스프레소")
    private String description;

    @Schema(example = "0123456789")
    private String barcode;

    @Schema(example = "2024-02-25")
    private LocalDate expiryDate;

    @Schema(example = "LARGE")
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
