package com.payhere.homework.api.presentation.domain.product.dto;

import com.payhere.homework.core.db.domain.product.ProductCategory;
import com.payhere.homework.core.db.domain.product.ProductSize;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(example = "에스레소")
    @NotEmpty(message = "상품명을 필수입니다")
    private String name;

    @Schema(example = "COFFEE")
    @NotNull(message = "카테고리는 필수입니다")
    private ProductCategory category;

    @Schema(example = "1500")
    @NotNull(message = "가격은 필수입니다")
    private BigDecimal price;

    @Schema(example = "1000")
    @NotNull(message = "원가는 필수입니다")
    private BigDecimal costPrice;

    @Schema(example = "향이좋은 에스프레소 입니다")
    @NotEmpty(message = "설명은 필수입니다")
    private String description;

    @Schema(example = "0123456789")
    @NotEmpty(message = "바코드는 필수입니다")
    private String barcode;

    @Schema(example = "2024-02-25")
    @NotNull(message = "유통기한은 필수입니다")
    private LocalDate expiryDate;

    @Schema(example = "LARGE")
    @NotNull(message = "사이즈는 필수입니다")
    private ProductSize size;

}
