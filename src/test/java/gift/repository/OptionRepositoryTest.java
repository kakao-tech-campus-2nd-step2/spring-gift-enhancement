package gift.repository;

import gift.model.product.Category;
import gift.model.product.Option;
import gift.model.product.Product;
import gift.model.product.ProductName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class OptionRepositoryTest {

    @Autowired
    private OptionRepository optionRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void save(){
        Product product = new Product(new Category("category1"),new ProductName("product1"),1000,"qwer.com");
        Option option = new Option(product,"option1",1234);
        Option option1 = optionRepository.save(option);
        assertAll(
                () -> assertThat(option1.getId()).isNotNull(),
                () -> assertThat(option1.getName()).isEqualTo(option.getName())
        );
    }
    @Test
    void purchaseProductById() {
        Category category = new Category("category1");
        categoryRepository.save(category);
        Product expectedProduct = new Product(category, new ProductName("product1"), 1000, "qwer.com");
        productRepository.save(expectedProduct);

        Option option = new Option(expectedProduct, "option1", 1000);
        optionRepository.save(option);

        optionRepository.subtractById(option.getId(), 100);

        Option updatedOption = optionRepository.findById(option.getId()).orElseThrow();
        assertEquals(900, updatedOption.getAmount());
    }
}