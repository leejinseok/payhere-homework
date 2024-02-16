package com.payhere.homework.core.db.domain.product;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.payhere.homework.core.db.domain.owner.QShopOwner.shopOwner;
import static com.payhere.homework.core.db.domain.product.QProduct.product;

@Repository
@RequiredArgsConstructor
public class ProductQueryDslRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public Page<Product> findPage(final Long shopOwnerId, final String productName, final Pageable pageable) {
        BooleanExpression where = product.shopOwner.id.eq(shopOwnerId);

        if (productName != null) {
            where.and(product.name.contains(productName));
            where.or(product.nameInitials.contains(productName));
        }

        List<Product> fetch = jpaQueryFactory
                .select(product)
                .from(product)
                .join(product.shopOwner, shopOwner).fetchJoin()
                .where(where)
                .orderBy(product.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long count = jpaQueryFactory
                .select(product.count())
                .from(product)
                .where(where)
                .fetchOne();

        return new PageImpl<>(fetch, pageable, count);
    }

}
