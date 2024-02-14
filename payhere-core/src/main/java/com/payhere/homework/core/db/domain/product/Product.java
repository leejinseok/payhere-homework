package com.payhere.homework.core.db.domain.product;

import com.payhere.homework.core.db.audit.AuditingDomain;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
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
    private String category;

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

}
