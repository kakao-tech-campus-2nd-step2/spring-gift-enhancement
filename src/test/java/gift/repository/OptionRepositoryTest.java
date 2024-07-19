package gift.repository;

import static org.assertj.core.api.Assertions.assertThat;

import gift.model.Category;
import gift.model.Option;
import gift.model.Product;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@Transactional
public class OptionRepositoryTest {

    @Autowired
    private OptionRepository optionRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    @DisplayName("옵션 저장 및 ID로 조회")
    public void testSaveAndFindById() {
        Category category = new Category("Test Category", "#000000", "imageUrl", "description");
        categoryRepository.save(category);

        Product product = new Product(null, "Test Product", 100, "test.jpg", category, List.of());
        Option option = new Option(null, "Option1", 100, product);
        product.setOptions(List.of(option));
        Product savedProduct = productRepository.save(product);

        Option savedOption = optionRepository.save(option);

        Optional<Option> foundOption = optionRepository.findById(savedOption.getId());

        assertThat(foundOption).isPresent();
        assertThat(foundOption.get().getName()).isEqualTo("Option1");
        assertThat(foundOption.get().getProduct().getId()).isEqualTo(savedProduct.getId());
    }

    @Test
    @DisplayName("모든 옵션 조회")
    public void testFindAll() {
        Category category = new Category("Test Category", "#000000", "imageUrl", "description");
        categoryRepository.save(category);

        Product product = new Product(null, "Test Product", 100, "test.jpg", category, List.of());
        Option option1 = new Option(null, "Option1", 100, product);
        Option option2 = new Option(null, "Option2", 200, product);
        product.setOptions(List.of(option1, option2));
        Product savedProduct = productRepository.save(product);

        optionRepository.save(option1);
        optionRepository.save(option2);

        Iterable<Option> options = optionRepository.findAll();
        assertThat(options).hasSize(2);
    }

    @Test
    @DisplayName("옵션 삭제")
    public void testDelete() {
        Category category = new Category("Test Category", "#000000", "imageUrl", "description");
        categoryRepository.save(category);

        Product product = new Product(null, "Test Product", 100, "test.jpg", category, List.of());
        Option option = new Option(null, "Option1", 100, product);
        product.setOptions(List.of(option));
        Product savedProduct = productRepository.save(product);

        Option savedOption = optionRepository.save(option);

        optionRepository.deleteById(savedOption.getId());
        Optional<Option> foundOption = optionRepository.findById(savedOption.getId());

        assertThat(foundOption).isNotPresent();
    }

    @Test
    @DisplayName("옵션 업데이트")
    public void testUpdate() {
        Category category = new Category("Test Category", "#000000", "imageUrl", "description");
        categoryRepository.save(category);

        Product product = new Product(null, "Test Product", 100, "test.jpg", category, List.of());
        Option option = new Option(null, "Option1", 100, product);
        product.setOptions(List.of(option));
        Product savedProduct = productRepository.save(product);

        Option savedOption = optionRepository.save(option);

        savedOption.update("Updated Option", 200, savedProduct);
        Option updatedOption = optionRepository.save(savedOption);

        Optional<Option> foundOption = optionRepository.findById(updatedOption.getId());

        assertThat(foundOption).isPresent();
        assertThat(foundOption.get().getName()).isEqualTo("Updated Option");
        assertThat(foundOption.get().getQuantity()).isEqualTo(200);
    }
}
