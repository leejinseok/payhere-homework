package com.payhere.homework.core.db.domain.product;

import com.payhere.homework.core.db.audit.AuditingDomain;
import com.payhere.homework.core.db.domain.owner.ShopOwner;
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
                @UniqueConstraint(columnNames = {"barcode"}, name = "product_uk_1")
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
    private String nameInitials;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_owner_id")
    private ShopOwner shopOwner;

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

    public boolean doesShopOwnerOwnThisProduct(final Long shopOwnerId) {
        return this.shopOwner.getId().equals(shopOwnerId);
    }

}
