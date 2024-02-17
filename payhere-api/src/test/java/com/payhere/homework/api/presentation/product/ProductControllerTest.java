package com.payhere.homework.api.presentation.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.payhere.homework.api.application.config.ApiSecurityConfig;
import com.payhere.homework.api.application.config.jwt.JwtConfig;
import com.payhere.homework.api.application.config.jwt.JwtProvider;
import com.payhere.homework.api.application.domain.product.ProductService;
import com.payhere.homework.api.presentation.domain.product.ProductController;
import com.payhere.homework.api.presentation.domain.product.dto.ProductRequest;
import com.payhere.homework.core.db.domain.owner.ShopOwner;
import com.payhere.homework.core.db.domain.product.Product;
import com.payhere.homework.core.db.domain.product.ProductCategory;
import com.payhere.homework.core.db.domain.product.ProductSize;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {ProductController.class})
@Import({ApiSecurityConfig.class, JwtConfig.class})
class ProductControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    JwtProvider jwtProvider;

    @MockBean
    ProductService productService;

    public ShopOwner sampleShopOwner() {
        return ShopOwner.builder()
                .id(1L)
                .phoneNumber("01022223333")
                .build();
    }

    public ProductRequest sampleSaveRequest() {
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

    public Product sampleProduct() {
        Product build = Product.builder()
                .id(1L)
                .name("에스프레소")
                .category(ProductCategory.COFFEE)
                .price(new BigDecimal(1500))
                .costPrice(new BigDecimal(1000))
                .description("에스프레소 입니다")
                .barcode("0123456789")
                .expiryDate(LocalDate.now().plusDays(3))
                .size(ProductSize.SMALL)
                .shopOwner(sampleShopOwner())
                .build();

        build.setCreatedAt(LocalDateTime.now());
        build.setLastModifiedAt(LocalDateTime.now());
        return build;
    }

    @Test
    void 상품조회() throws Exception {
        Product product = sampleProduct();
        PageImpl<Product> products = new PageImpl<>(
                List.of(product),
                PageRequest.of(0, 10),
                1
        );

        when(productService.findPage(any(), any(), any())).thenReturn(products);

        ShopOwner shopOwner = sampleShopOwner();
        String token = jwtProvider.createToken(shopOwner);

        mockMvc.perform(
                        get("/api/v1/products")
                                .param("page", "0")
                                .param("size", "10")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Bearer " + token)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.meta.code").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.meta.message").value(HttpStatus.OK.name()))
                .andExpect(jsonPath("$.data.pageInfo.currentPage").exists())
                .andExpect(jsonPath("$.data.pageInfo.totalPages").exists())
                .andExpect(jsonPath("$.data.pageInfo.size").exists())
                .andExpect(jsonPath("$.data.pageInfo.totalElements").exists())
                .andExpect(jsonPath("$.data.pageInfo.sort").exists())
                .andExpect(jsonPath("$.data.pageInfo.last").exists())
                .andExpect(jsonPath("$.data.list[0]").exists());
    }

    @Test
    void 상품조회_실패_로그인하지않은_사용자() throws Exception {
        mockMvc.perform(
                        get("/api/v1/products")
                                .param("page", "0")
                                .param("size", "10")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    void 상품조회_단건() throws Exception {
        Product product = sampleProduct();
        when(productService.findOne(any(), any())).thenReturn(product);

        ShopOwner shopOwner = sampleShopOwner();
        String token = jwtProvider.createToken(shopOwner);

        mockMvc.perform(
                        get("/api/v1/products/" + product.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Bearer " + token)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.meta.code").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.meta.message").value(HttpStatus.OK.name()))
                .andExpect(jsonPath("$.data.id").value(product.getId()))
                .andExpect(jsonPath("$.data.name").value(product.getName()))
                .andExpect(jsonPath("$.data.category").value(product.getCategory().name()))
                .andExpect(jsonPath("$.data.price").value(product.getPrice()))
                .andExpect(jsonPath("$.data.costPrice").value(product.getCostPrice()))
                .andExpect(jsonPath("$.data.description").value(product.getDescription()))
                .andExpect(jsonPath("$.data.barcode").value(product.getBarcode()))
                .andExpect(jsonPath("$.data.expiryDate").exists())
                .andExpect(jsonPath("$.data.size").value(product.getSize().name()))
                .andExpect(jsonPath("$.data.shopOwner.id").value(product.getShopOwner().getId()))
                .andExpect(jsonPath("$.data.createdAt").exists())
                .andExpect(jsonPath("$.data.lastModifiedAt").exists());
    }

    @Test
    void 상품조회_단건_실패_로그인하지않은_사용자() throws Exception {
        mockMvc.perform(
                        get("/api/v1/products/1")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    void 상품등록() throws Exception {
        ProductRequest saveRequest = sampleSaveRequest();
        byte[] content = objectMapper.writeValueAsBytes(saveRequest);

        ShopOwner shopOwner = sampleShopOwner();
        String token = jwtProvider.createToken(shopOwner);

        Product product = Product.builder()
                .id(1L)
                .name(saveRequest.getName())
                .price(saveRequest.getPrice())
                .shopOwner(shopOwner)
                .barcode(saveRequest.getBarcode())
                .category(saveRequest.getCategory())
                .costPrice(saveRequest.getCostPrice())
                .description(saveRequest.getDescription())
                .expiryDate(saveRequest.getExpiryDate())
                .size(saveRequest.getSize())
                .build();
        product.setCreatedAt(LocalDateTime.now());
        product.setLastModifiedAt(LocalDateTime.now());

        when(productService.save(any(), any())).thenReturn(product);

        mockMvc.perform(
                        post("/api/v1/products")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content)
                                .header("Authorization", "Bearer " + token)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.meta.code").value(HttpStatus.CREATED.value()))
                .andExpect(jsonPath("$.meta.message").value(HttpStatus.CREATED.name()))
                .andExpect(jsonPath("$.data.id").value(product.getId()))
                .andExpect(jsonPath("$.data.name").value(product.getName()))
                .andExpect(jsonPath("$.data.category").value(product.getCategory().name()))
                .andExpect(jsonPath("$.data.price").value(product.getPrice()))
                .andExpect(jsonPath("$.data.costPrice").value(product.getCostPrice()))
                .andExpect(jsonPath("$.data.description").value(product.getDescription()))
                .andExpect(jsonPath("$.data.barcode").value(product.getBarcode()))
                .andExpect(jsonPath("$.data.expiryDate").exists())
                .andExpect(jsonPath("$.data.size").value(product.getSize().name()))
                .andExpect(jsonPath("$.data.shopOwner.id").value(product.getShopOwner().getId()))
                .andExpect(jsonPath("$.data.createdAt").exists())
                .andExpect(jsonPath("$.data.lastModifiedAt").exists());
    }

    @Test
    void 상품등록_실패_로그인하지않은_사용자() throws Exception {
        mockMvc.perform(
                        post("/api/v1/products")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    void 상품수정() throws Exception {
        ProductRequest request = new ProductRequest();
        byte[] content = objectMapper.writeValueAsBytes(request);
        Product product = sampleProduct();

        when(productService.update(any(), any(), any())).thenReturn(product);

        ShopOwner shopOwner = sampleShopOwner();
        String token = jwtProvider.createToken(shopOwner);

        mockMvc.perform(
                        patch("/api/v1/products/" + product.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content)
                                .header("Authorization", "Bearer " + token)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.meta.code").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.meta.message").value(HttpStatus.OK.name()))
                .andExpect(jsonPath("$.data.id").value(product.getId()))
                .andExpect(jsonPath("$.data.name").value(product.getName()))
                .andExpect(jsonPath("$.data.category").value(product.getCategory().name()))
                .andExpect(jsonPath("$.data.price").value(product.getPrice()))
                .andExpect(jsonPath("$.data.costPrice").value(product.getCostPrice()))
                .andExpect(jsonPath("$.data.description").value(product.getDescription()))
                .andExpect(jsonPath("$.data.barcode").value(product.getBarcode()))
                .andExpect(jsonPath("$.data.expiryDate").exists())
                .andExpect(jsonPath("$.data.size").value(product.getSize().name()))
                .andExpect(jsonPath("$.data.shopOwner.id").value(product.getShopOwner().getId()))
                .andExpect(jsonPath("$.data.createdAt").exists())
                .andExpect(jsonPath("$.data.lastModifiedAt").exists());
    }

    @Test
    void 상품수정_실패_로그인하지않은_사용자() throws Exception {
        ProductRequest request = new ProductRequest();
        byte[] content = objectMapper.writeValueAsBytes(request);

        mockMvc.perform(
                        patch("/api/v1/products/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content)
                )
                .andDo(print())
                .andExpect(status().isForbidden());

    }

    @Test
    void 상품삭제() throws Exception {
        ShopOwner shopOwner = sampleShopOwner();
        String token = jwtProvider.createToken(shopOwner);

        mockMvc.perform(
                        delete("/api/v1/products/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Bearer " + token)
                )
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(productService, times(1)).delete(any(), any());
    }

    @Test
    void 상품삭제_실패_로그인하지않은_사용자() throws Exception {
        mockMvc.perform(
                        delete("/api/v1/products/1")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isForbidden());
    }
}