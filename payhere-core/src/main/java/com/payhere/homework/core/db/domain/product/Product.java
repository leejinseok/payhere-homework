package com.payhere.homework.core.db.domain.product;

import com.payhere.homework.core.db.audit.AuditingDomain;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Entity
@Table(
        name = "product",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"barcode"})
        }
)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product extends AuditingDomain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    @Enumerated(EnumType.STRING)
    private ProductCategory category;

    @Column
    private BigDecimal price;

    @Column
    private BigDecimal costPrice;

    @Column
    private String description;

    @Column
    private String barcode;

    @Column
    private LocalDate expiryDate;

    @Column
    @Enumerated(EnumType.STRING)
    private ProductSize size;

    public void updateName(final String name) {
        this.name = name;
    }

    public void updateCategory(final ProductCategory category) {
        this.category = category;
    }

    public void updatePrice(final BigDecimal price) {
        this.price = price;
    }

    public void updateCostPrice(final BigDecimal costPrice) {
        this.costPrice = costPrice;
    }

    public void updateDescription(final String description) {
        this.description = description;
    }

    public void updateBarcode(final String barcode) {
        this.barcode = barcode;
    }

    public void updateExpiryDate(final LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public void updateSize(final ProductSize size) {
        this.size = size;
    }

}
