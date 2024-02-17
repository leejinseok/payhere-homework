package com.payhere.homework.api.application.domain.product;

import com.payhere.homework.api.application.config.ApiCacheConfig;
import com.payhere.homework.api.application.config.ApiDbConfig;
import com.payhere.homework.api.application.exception.NotFoundException;
import com.payhere.homework.api.application.exception.UnauthorizedException;
import com.payhere.homework.api.application.util.HangulUtil;
import com.payhere.homework.api.presentation.domain.product.dto.ProductRequest;
import com.payhere.homework.core.db.domain.owner.ShopOwner;
import com.payhere.homework.core.db.domain.owner.ShopOwnerRepository;
import com.payhere.homework.core.db.domain.product.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles({"test"})
@DataJpaTest
@Import({ApiDbConfig.class, ApiCacheConfig.class})
class ProductServiceTest {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductQueryDslRepository productQueryDslRepository;

    @Autowired
    ShopOwnerRepository shopOwnerRepository;

    @Autowired
    CacheManager cacheManager;

    ShopOwner createSampleShopOwner() {
        return shopOwnerRepository.save(
                ShopOwner.builder()
                        .phoneNumber("01022223333")
                        .password("password")
                        .build()
        );
    }

    ProductRequest createSampleProductRequest() {
        return ProductRequest.of(
                "에스프레소",
                ProductCategory.COFFEE,
                new BigDecimal(1500),
                new BigDecimal(1000),
                "에스프레소 입니다",
                "0123456789",
                LocalDate.now().plusDays(3),
                ProductSize.SMALL
        );
    }

    ProductService createProductService() {
        return new ProductService(productRepository, productQueryDslRepository, shopOwnerRepository, cacheManager);
    }

    @Test
    void 상품조회_단건() {
        ShopOwner shopOwner = createSampleShopOwner();
        ProductRequest productRequest = createSampleProductRequest();

        ProductService productService = createProductService();
        Product save = productService.save(shopOwner.getId(), productRequest);

        Product product = productService.findOne(shopOwner.getId(), save.getId());

        assertThat(product.getId()).isNotNull();
        assertThat(product.getName()).isEqualTo(productRequest.getName());
        assertThat(product.getBarcode()).isEqualTo(productRequest.getBarcode());
        assertThat(product.getPrice()).isEqualTo(productRequest.getPrice());
        assertThat(product.getCostPrice()).isEqualTo(productRequest.getCostPrice());
        assertThat(product.getExpiryDate()).isEqualTo(productRequest.getExpiryDate());
    }

    @Test
    void 상품조회_페이지() {
        ShopOwner shopOwner = createSampleShopOwner();
        ProductRequest productRequest = createSampleProductRequest();
        ProductService productService = createProductService();
        Product save = productService.save(shopOwner.getId(), productRequest);

        PageRequest pageRequest = PageRequest.of(0, 10);

        char firstChar = HangulUtil.splitHangul(save.getName().charAt(0))[0];

        Page<Product> page = productService.findPage(
                shopOwner.getId(),
                String.valueOf(firstChar),
                pageRequest
        );

        assertThat(page.getTotalElements()).isGreaterThan(0);
    }

    @Test
    void 상품등록() {
        ShopOwner shopOwner = createSampleShopOwner();
        ProductService productService = createProductService();
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

        Product save = productService.save(shopOwner.getId(), saveRequest);
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
        ShopOwner shopOwner = createSampleShopOwner();

        ProductService productService = createProductService();
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

        Product save = productService.save(shopOwner.getId(), saveRequest);
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

        Product update = productService.update(shopOwner.getId(), save.getId(), updateRequest);
        assertThat(update.getId()).isNotNull();
        assertThat(update.getName()).isEqualTo(updateRequest.getName());
        assertThat(update.getBarcode()).isEqualTo(updateRequest.getBarcode());
        assertThat(update.getPrice()).isEqualTo(updateRequest.getPrice());
        assertThat(update.getCostPrice()).isEqualTo(updateRequest.getCostPrice());
        assertThat(update.getExpiryDate()).isEqualTo(updateRequest.getExpiryDate());
    }

    @Test
    void 상품수정_실패_권한없음() {
        ShopOwner shopOwner = createSampleShopOwner();
        ProductService productService = createProductService();

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

        Product save = productService.save(shopOwner.getId(), saveRequest);
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

        assertThrows(UnauthorizedException.class, () -> {
            productService.update(2L, save.getId(), updateRequest);
        });
    }

    @Test
    void 상품삭제() {
        ShopOwner shopOwner = createSampleShopOwner();
        ProductService productService = createProductService();

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

        Product save = productService.save(shopOwner.getId(), saveRequest);
        productService.delete(shopOwner.getId(), save.getId());

        assertThrows(NotFoundException.class, () -> {
            productService.findById(save.getId());
        });
    }

    @Test
    void 상품삭제_실패_권한없음() {
        ShopOwner shopOwner = createSampleShopOwner();
        ProductService productService = createProductService();

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

        Product save = productService.save(shopOwner.getId(), saveRequest);

        Long shopOwnerIdNotHaveDeletePermission = shopOwner.getId() + 1;
        assertThrows(UnauthorizedException.class, () -> {
            productService.delete(shopOwnerIdNotHaveDeletePermission, save.getId());
        });
    }


}