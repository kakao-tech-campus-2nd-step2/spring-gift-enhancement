package gift.Product;

import static org.assertj.core.api.Assertions.assertThat;
import gift.initializer.CategoryInitializer;
import gift.model.Category;
import gift.model.Product;
import gift.repository.CategoryRepository;
import gift.repository.ProductRepository;
import gift.service.CategoryService;
import java.util.Optional;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProductRepositoryTest {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;
    // this.categoryRepository = categoryRepository;


    private CategoryInitializer categoryInitializer;

    private CategoryService categoryService;

    @BeforeEach
    public void set() {
        this.categoryService = new CategoryService(categoryRepository);
        this.categoryInitializer = new CategoryInitializer(categoryRepository);
    }


    @Test
    void testFindById() {
        Category category = new Category("교환권");
        categoryRepository.save(category);
        Category category1 = categoryService.getCategoryById(1L);
        Product expected = new Product("카푸치노", 3000, "example.com",category1);
        productRepository.save(expected);
        Optional<Product> actual = productRepository.findById(expected.getId());
        assertThat(actual.isPresent()).isTrue();
        assertThat(actual.get().getName()).isEqualTo("카푸치노");
    }

    @Test
    void save(){
        Category category = new Category("교환권");
        categoryRepository.save(category);
        Category category1 = categoryService.getCategoryById(1L);
        Product expected = new Product("초코라떼", 3500, "example2.com",category1);
        Product actual = productRepository.save(expected);
        assertThat(actual.getId()).isEqualTo(expected.getId());
    }

    @Test
    void delete(){
        Category category = new Category("교환권");
        categoryRepository.save(category);
        Category category1 = categoryService.getCategoryById(1L);
        Product expected = new Product("그린티라떼", 3500, "example3.com",category1);
        productRepository.save(expected);
        productRepository.deleteById(expected.getId());
        Optional<Product> actual = productRepository.findById(expected.getId());
        assertThat(actual).isNotPresent();
    }

}
