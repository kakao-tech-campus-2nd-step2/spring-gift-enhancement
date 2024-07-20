//package gift.repositories;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//import gift.domain.Category;
//import gift.domain.Option;
//import gift.domain.Product;
//import java.util.List;
//import java.util.Optional;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//
//@DataJpaTest
//class ProductRepositoryTest {
//
//    @Autowired
//    private ProductRepository productRepository;
//
//    @Autowired
//    private CategoryRepository categoryRepository;
//
//    @Test
//    @DisplayName("제품 저장 테스트")
//    public void testSaveProduct() {
//        Category category = new Category("Test Category", "Blue", "abcd", "Test");
//        Option option = new Option("Test Option", 300, )
//        categoryRepository.save(category);
//        Product product = new Product(null, "Test Product", 1000, "http://example.com/test.jpg",
//            category);
//        Product savedProduct = productRepository.save(product);
//
//        assertThat(savedProduct).isNotNull();
//        assertThat(savedProduct.getId()).isNotNull();
//        assertThat(savedProduct.getName()).isEqualTo("Test Product");
//        assertThat(savedProduct.getPrice()).isEqualTo(1000);
//        assertThat(savedProduct.getImageUrl()).isEqualTo("http://example.com/test.jpg");
//    }
//
//    @Test
//    @DisplayName("ID로 제품 조회 테스트")
//    public void testFindById() {
//        Category category = new Category("Test Category", "Blue", "abcd", "Test");
//        categoryRepository.save(category);
//        Product product = new Product(null, "Test Product", 1000, "http://example.com/test.jpg",
//            category);
//        Product savedProduct = productRepository.save(product);
//
//        Optional<Product> foundProduct = productRepository.findById(savedProduct.getId());
//
//        assertThat(foundProduct).isPresent();
//        assertThat(foundProduct.get().getName()).isEqualTo("Test Product");
//    }
//
//    @Test
//    @DisplayName("모든 제품 조회 테스트")
//    public void testFindAll() {
//        Category category1 = new Category("Test Category1", "Blue", "abcd", "Test");
//        Category category2 = new Category("Test Category2", "Blue", "abcd", "Test");
//        categoryRepository.save(category1);
//        categoryRepository.save(category2);
//
//        Product product1 = new Product(null, "Test Product 1", 1000,
//            "http://example.com/test1.jpg", category1);
//        Product product2 = new Product(null, "Test Product 2", 2000,
//            "http://example.com/test2.jpg", category2);
//        productRepository.save(product1);
//        productRepository.save(product2);
//
//        List<Product> products = productRepository.findAll();
//
//        assertThat(products).hasSize(2);
//        assertThat(products).extracting(Product::getName)
//            .containsExactlyInAnyOrder("Test Product 1", "Test Product 2");
//    }
//
//    @Test
//    @DisplayName("ID로 제품 삭제 테스트")
//    public void testDeleteById() {
//        Category category = new Category("Test Category", "Blue", "abcd", "Test");
//        categoryRepository.save(category);
//        Product product = new Product(null, "Test Product", 1000, "http://example.com/test.jpg",
//            category);
//        Product savedProduct = productRepository.save(product);
//
//        productRepository.deleteById(savedProduct.getId());
//
//        Optional<Product> deletedProduct = productRepository.findById(savedProduct.getId());
//        assertThat(deletedProduct).isNotPresent();
//    }
//
//    @Test
//    @DisplayName("제품 업데이트 테스트")
//    public void testUpdateProduct() {
//        Category category = new Category("Test Category", "Blue", "abcd", "Test");
//        categoryRepository.save(category);
//        Product product = new Product(null, "Test Product", 1000, "http://example.com/test.jpg",
//            category);
//        Product savedProduct = productRepository.save(product);
//
//        savedProduct.setName("Updated Product");
//        savedProduct.setPrice(2000);
//        savedProduct.setImageUrl("http://example.com/updated.jpg");
//        Product updatedProduct = productRepository.save(savedProduct);
//
//        assertThat(updatedProduct.getName()).isEqualTo("Updated Product");
//        assertThat(updatedProduct.getPrice()).isEqualTo(2000);
//        assertThat(updatedProduct.getImageUrl()).isEqualTo("http://example.com/updated.jpg");
//    }
//
//}