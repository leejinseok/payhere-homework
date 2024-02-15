package com.payhere.homework.api.presentation.product.dto;

import com.payhere.homework.core.db.domain.product.ProductCategory;
import com.payhere.homework.core.db.domain.product.ProductSize;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class ProductRequest {

    @NotEmpty(message = "상품명을 필수입니다")
    private String name;

    @NotNull(message = "카테고리는 필수입니다")
    private ProductCategory category;

    @NotNull(message = "가격은 필수입니다")
    private BigDecimal price;

    @NotNull(message = "원가는 필수입니다")
    private BigDecimal costPrice;

    @NotEmpty(message = "설명은 필수입니다")
    private String description;

    @NotEmpty(message = "바코드는 필수입니다")
    private String barcode;

    @NotNull(message = "유통기한은 필수입니다")
    private LocalDate expiryDate;

    @NotNull(message = "사이즈는 필수입니다")
    private ProductSize size;

}
