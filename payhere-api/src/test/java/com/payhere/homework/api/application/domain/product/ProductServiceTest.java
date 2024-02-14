package com.payhere.homework.api.application.domain.product;

import com.payhere.homework.api.application.config.ApiDbConfig;
import com.payhere.homework.api.presentation.product.dto.ProductRequest;
import com.payhere.homework.core.db.domain.owner.ShopOwnerRepository;
import com.payhere.homework.core.db.domain.product.Product;
import com.payhere.homework.core.db.domain.product.ProductCategory;
import com.payhere.homework.core.db.domain.product.ProductRepository;
import com.payhere.homework.core.db.domain.product.ProductSize;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles({"test"})
@DataJpaTest
@Import({ApiDbConfig.class})
class ProductServiceTest {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ShopOwnerRepository shopOwnerRepository;

    @Test
    void 상품등록() {
        ProductService productService = new ProductService(productRepository, shopOwnerRepository);

        ProductRequest saveRequest = ProductRequest.of(
                "에스프레소",
                ProductCategory.COFFEE,
                new BigDecimal(1500),
                new BigDecimal(1000),
                "에스프레소 입니다",
                "0123456789",
                LocalDate.now().plusDays(3),
                ProductSize.SMALL
        );

        Product save = productService.save(1L, saveRequest);
        assertThat(save.getId()).isNotNull();
        assertThat(save.getName()).isEqualTo(saveRequest.getName());
        assertThat(save.getBarcode()).isEqualTo(saveRequest.getBarcode());
        assertThat(save.getPrice()).isEqualTo(saveRequest.getPrice());
        assertThat(save.getCostPrice()).isEqualTo(saveRequest.getCostPrice());
        assertThat(save.getExpiryDate()).isEqualTo(saveRequest.getExpiryDate());

        productRepository.deleteById(save.getId());
    }

    @Test
    void 상품수정() {
        ProductService productService = new ProductService(productRepository, shopOwnerRepository);
        ProductRequest saveRequest = ProductRequest.of(
                "에스프레소",
                ProductCategory.COFFEE,
                new BigDecimal(1500),
                new BigDecimal(1000),
                "에스프레소 입니다",
                "0123456789",
                LocalDate.now().plusDays(3),
                ProductSize.SMALL
        );

        Product save = productService.save(1L, saveRequest);
        ProductRequest updateRequest = ProductRequest.of(
                "아메리카노",
                ProductCategory.COFFEE,
                new BigDecimal(2000),
                new BigDecimal(1500),
                "아메리카노 입니다",
                "1234567890",
                LocalDate.now().plusDays(2),
                ProductSize.LARGE
        );

        Product update = productService.update(1L, save.getId(), updateRequest);
        assertThat(update.getId()).isNotNull();
        assertThat(update.getName()).isEqualTo(updateRequest.getName());
        assertThat(update.getBarcode()).isEqualTo(updateRequest.getBarcode());
        assertThat(update.getPrice()).isEqualTo(updateRequest.getPrice());
        assertThat(update.getCostPrice()).isEqualTo(updateRequest.getCostPrice());
        assertThat(update.getExpiryDate()).isEqualTo(updateRequest.getExpiryDate());
    }


}