package com.payhere.homework.api.presentation.product.dto;

import com.payhere.homework.core.db.domain.product.ProductSize;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
public class ProductSaveRequest {

    @NotEmpty(message = "상품명을 필수입니다")
    private String name;

    @NotEmpty(message = "카테고리는 필수입니다")
    private String category;

    @NotEmpty(message = "가격은 필수입니다")
    private BigDecimal price;

    @NotEmpty(message = "원가는 필수입니다")
    private BigDecimal costPrice;

    @NotEmpty(message = "설명은 필수입니다")
    private String description;

    @NotEmpty(message = "바코드는 필수입니다")
    private String barcode;

    @NotEmpty(message = "유통기한은 필수입니다")
    private LocalDate expiryDate;

    @NotEmpty(message = "사이즈는 필수입니다")
    private ProductSize size;


}
