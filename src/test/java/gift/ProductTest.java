package gift;

import gift.model.Name;
import gift.model.Option;
import gift.model.OptionName;
import gift.model.OptionQuantity;
import gift.model.Product;
import gift.repository.OptionRepository;
import gift.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProductTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OptionRepository optionRepository;

    @Test
    void whenProductIsCreated_thenProductSavedCorrectly() {
        Name name = new Name("TestProduct");
        List<Option> options = new ArrayList<>();
        Product product = new Product(null, name, 100, "http://example.com/image.png", 1L, options);
        Product savedProduct = productRepository.save(product);

        assertAll(
            () -> assertNotNull(savedProduct.getId()),
            () -> assertEquals("TestProduct", savedProduct.getName().getName()),
            () -> assertEquals(100, savedProduct.getPrice()),
            () -> assertEquals("http://example.com/image.png", savedProduct.getImageUrl()),
            () -> assertEquals(1L, savedProduct.getCategoryId())
        );
    }

    @Test
    void whenAddOption_thenOptionAdded() {
        Name name = new Name("TestProduct");
        List<Option> options = new ArrayList<>();
        Product product = new Product(null, name, 100, "http://example.com/image.png", 1L, options);
        productRepository.save(product);

        Option option = new Option(null, new OptionName("TestOption"), new OptionQuantity(10), product);
        product.addOption(option);
        optionRepository.save(option);

        Product savedProduct = productRepository.findById(product.getId()).orElseThrow();
        assertEquals(1, savedProduct.getOptions().size());
        assertEquals("TestOption", savedProduct.getOptions().get(0).getName().getName());
    }

    @Test
    void whenClearOptions_thenOptionsCleared() {
        Name name = new Name("TestProduct");
        List<Option> options = new ArrayList<>();
        Product product = new Product(null, name, 100, "http://example.com/image.png", 1L, options);
        productRepository.save(product);

        Option option1 = new Option(null, new OptionName("TestOption1"), new OptionQuantity(10), product);
        Option option2 = new Option(null, new OptionName("TestOption2"), new OptionQuantity(20), product);
        product.addOption(option1);
        product.addOption(option2);
        optionRepository.saveAll(List.of(option1, option2));

        product.clearOptions();
        productRepository.save(product);

        Product savedProduct = productRepository.findById(product.getId()).orElseThrow();
        assertEquals(0, savedProduct.getOptions().size());
    }

    @Test
    public void testUpdate() {
        // given
        Name originalName = new Name("Original Name");
        Name updatedName = new Name("Updated Name");
        List<Option> options = new ArrayList<>();
        Product product = new Product(1L, originalName, 100, "http://original.image.url", 1L, options);

        // when
        product.update(updatedName, 200, "http://updated.image.url", 2L);

        // then
        assertAll(
            () -> assertEquals(1L, product.getId()),
            () -> assertEquals(updatedName, product.getName()),
            () -> assertEquals(200, product.getPrice()),
            () -> assertEquals("http://updated.image.url", product.getImageUrl()),
            () -> assertEquals(2L, product.getCategoryId())
        );
    }
}