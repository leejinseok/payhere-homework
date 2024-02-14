package com.payhere.homework.core.db.domain.product;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.payhere.homework.core.db.domain.product.QProduct.product;

@Repository
@RequiredArgsConstructor
public class ProductQueryDslRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public long update(
            final Long productId,
            final String name,
            final ProductCategory category,
            final BigDecimal price,
            final BigDecimal costPrice,
            final String description,
            final String barcode,
            final LocalDate expiryDate,
            final ProductSize size
    ) {

        JPAUpdateClause update = jpaQueryFactory.update(product);

        if (name != null) {
            update.set(product.name, name);
        }
        if (category != null) {
            update.set(product.category, category);
        }

        if (price != null) {
            update.set(product.price, price);
        }

        if (costPrice != null) {
            update.set(product.costPrice, costPrice);
        }

        if (description != null) {
            update.set(product.description, description);
        }

        if (barcode != null) {
            update.set(product.barcode, barcode);
        }

        if (expiryDate != null) {
            update.set(product.expiryDate, expiryDate);
        }

        if (size != null) {
            update.set(product.size, size);
        }

        update.where(product.id.eq(productId));
        return update.execute();
    }

}
