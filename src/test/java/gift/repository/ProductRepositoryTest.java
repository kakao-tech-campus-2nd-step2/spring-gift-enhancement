package gift.repository;

import gift.dto.ProductDto;
import gift.model.member.Member;
import gift.model.product.Category;
import gift.model.product.Product;
import gift.model.product.ProductName;
import gift.model.wish.Wish;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    private Wish originWish;
    private Member expectedMember;
    private Product expectedProduct;
    private Category category;

    @BeforeEach
    public void setUp() {
        expectedMember = new Member("qwer@gmail.com","1234","root");
        category = new Category("category1");
        expectedProduct = new Product(category,new ProductName("product1"),1000,"qwer.com",1000);
        originWish = new Wish(expectedProduct,expectedMember,1000 );
    }

    @Test
    void save(){
        productRepository.save(expectedProduct);
        Optional<Product> actual = productRepository.findById(1L);
        assertAll(
                () -> assertThat(actual.get().getId()).isNotNull(),
                () -> assertThat(actual.get().getName()).isEqualTo(expectedProduct.getName())
        );
    }

    @Test
    void delete(){
        productRepository.save(expectedProduct);
        productRepository.delete(expectedProduct);
        Optional<Product> actual = productRepository.findById(1L);
        assertThat(actual).isEmpty();
    }

    @Test
    void update(){
        Product updatedProduct = new Product(category,new ProductName("product2"), 1500, "updated.com", 2000);

        Product savedProduct = productRepository.save(expectedProduct);
        savedProduct.updateProduct(updatedProduct);
        productRepository.save(savedProduct);

        Product fetchedProduct = productRepository.findById(savedProduct.getId()).orElse(null);
        assertThat(fetchedProduct.getName().getName()).isEqualTo(updatedProduct.getName());
    }

    @Test
    void existsByName() {
        Product savedProduct = productRepository.save(expectedProduct);
        assertThat(productRepository.existsById(savedProduct.getId())).isTrue();
    }

    @Test
    void purchaseProductById() {
        productRepository.save(expectedProduct);
        productRepository.purchaseProductById(1L, 100);
        Product savedProduct = productRepository.findById(1L).get();
        assertThat(savedProduct.getAmount()).isEqualTo(900);
    }
}