package gift.dao;

import gift.category.entity.Category;
import gift.product.dao.ProductRepository;
import gift.product.dto.ProductRequest;
import gift.product.entity.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    private final Category category = new Category.CategoryBuilder()
            .setName("상품권")
            .setColor("#ffffff")
            .setImageUrl("https://product-shop.com")
            .setDescription("")
            .build();

    @Test
    @DisplayName("상품 추가 및 ID 조회 테스트")
    void saveAndFindById() {
        Product product = new Product.ProductBuilder()
                .setName("newproduct")
                .setPrice(12345)
                .setImageUrl("new.jpg")
                .setCategory(category)
                .build();
        Product savedProduct = productRepository.save(product);

        Product foundProduct = productRepository.findById(savedProduct.getId())
                .orElse(null);

        assertThat(foundProduct).isNotNull();
        assertThat(foundProduct.getName()).isEqualTo(savedProduct.getName());
        assertThat(foundProduct.getPrice()).isEqualTo(savedProduct.getPrice());
        assertThat(foundProduct.getImageUrl()).isEqualTo(savedProduct.getImageUrl());
    }

    @Test
    @DisplayName("상품 ID 조회 실패 테스트")
    void findByIdFailed() {
        Product product = new Product.ProductBuilder()
                .setName("newproduct")
                .setPrice(12345)
                .setImageUrl("new.jpg")
                .setCategory(category)
                .build();
        productRepository.save(product);

        Product foundProduct = productRepository.findById(123456789L)
                .orElse(null);

        assertThat(foundProduct).isNull();
    }

    @Test
    @DisplayName("상품 ID 리스트 조회 테스트")
    void findByIds() {
        List<Long> productIds = new ArrayList<>();
        productIds.add(
                productRepository.save(
                        new Product.ProductBuilder()
                                .setName("product1L")
                                .setPrice(1000)
                                .setImageUrl("1L.jpg")
                                .setCategory(category)
                                .build()
                ).getId()
        );
        productIds.add(
                productRepository.save(
                        new Product.ProductBuilder()
                                .setName("product2L")
                                .setPrice(2000)
                                .setImageUrl("2L.jpg")
                                .setCategory(category)
                                .build()
                ).getId()
        );
        productIds.add(
                productRepository.save(
                        new Product.ProductBuilder()
                                .setName("product3L")
                                .setPrice(3000)
                                .setImageUrl("3L.jpg")
                                .setCategory(category)
                                .build()
                ).getId()
        );
        productIds.add(
                productRepository.save(
                        new Product.ProductBuilder()
                                .setName("product4L")
                                .setPrice(4000)
                                .setImageUrl("4L.jpg")
                                .setCategory(category)
                                .build()
                ).getId()
        );

        List<Product> products = productRepository.findAll();

        assertThat(products.size()).isEqualTo(productIds.size());
    }

    @Test
    @DisplayName("상품 수정 테스트")
    void updateProduct() {
        Product product = new Product.ProductBuilder()
                .setName("product1")
                .setPrice(1000)
                .setImageUrl("product1.jpg")
                .setCategory(category)
                .build();
        ProductRequest request = new ProductRequest(
                "updateproduct",
                12345,
                "updateproduct.jpg",
                category.getName()
        );
        Product savedProduct = productRepository.save(product);
        savedProduct.update(request, category);

        Product updatedProduct = productRepository.save(savedProduct);

        Product foundProduct = productRepository.findById(updatedProduct.getId())
                .orElse(null);
        assertThat(foundProduct).isNotNull();
        assertThat(foundProduct.getName()).isEqualTo(savedProduct.getName());
        assertThat(foundProduct.getPrice()).isEqualTo(savedProduct.getPrice());
        assertThat(foundProduct.getImageUrl()).isEqualTo(savedProduct.getImageUrl());
    }

    @Test
    @DisplayName("상품 삭제 테스트")
    void deleteProduct() {
        Product product = new Product.ProductBuilder()
                .setName("product1")
                .setPrice(1000)
                .setImageUrl("product1.jpg")
                .setCategory(category)
                .build();
        Product savedProduct = productRepository.save(product);

        productRepository.deleteById(savedProduct.getId());

        boolean exists = productRepository.existsById(savedProduct.getId());
        assertThat(exists).isFalse();
    }

}