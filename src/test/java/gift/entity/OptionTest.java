package gift.entity;

import gift.dto.OptionRequestDTO;
import gift.exception.optionException.OptionException;
import gift.repository.CategoryRepository;
import gift.repository.OptionRepository;
import gift.repository.ProductRepository;
import gift.service.CategoryService;
import gift.service.OptionService;
import gift.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import jakarta.validation.ConstraintViolationException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@Transactional
public class OptionTest {

    @Autowired
    private OptionRepository optionRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @Autowired
    private OptionService optionService;

    private Category category;
    private Product product;

    @BeforeEach
    public void setUp() {
        // Set up Category and Product for testing
        category = new Category("Electronics", "Black", "Various electronic items", "http://example.com/image.jpg");
        product = new Product("Smartphone", 300, "http://example.com/smartphone.jpg", category);
        category.addProduct(product);
        // Save the category and product to the database
        categoryRepository.save(category);
        productRepository.save(product);
    }


    @Test
    public void testCreateInvalidOptionName() {
        Option option = new Option("Invalid Option Name !@#", 100, product);

        assertThrows(ConstraintViolationException.class, () -> {
            optionRepository.save(option);
            optionRepository.flush();
        });
    }

    @Test
    public void testCreateInvalidOptionQuantity() {
        Option option = new Option("Option 2", -780, product);

        assertThrows(ConstraintViolationException.class, () -> {
            optionRepository.save(option);
            optionRepository.flush();
        });
    }

    @Test
    public void testAddDuplicateOptionName() {
        OptionRequestDTO dto1 = new OptionRequestDTO(product.getId(), "Duplicate Option", 100);
        OptionRequestDTO dto2 = new OptionRequestDTO(product.getId(), "Duplicate Option", 10000);
        assertThrows(OptionException.class, () -> {
            optionService.addOption(dto1);
            optionService.addOption(dto2);
            optionRepository.flush();
        });

    }

    @Test
    public void testUpdateOption() {

        Option option = new Option("Option 1", 1000, product);
        OptionRequestDTO optionRequestDTO = new OptionRequestDTO(option.getId(), "Updated Option", 200);
        option.updateOption(optionRequestDTO);
        optionRepository.save(option);
        Option updatedOption = optionRepository.findById(option.getId())
                .orElseThrow(() -> new OptionException("option exception"));
        assertEquals("Updated Option", updatedOption.getName(), "Option name should be updated.");
        assertEquals(200, updatedOption.getQuantity(), "Option quantity should be updated.");

    }

    @Test
    public void testSubtractQuantity() {
        Option option = new Option("Option to Subtract", 100, product);
        option = optionRepository.save(option);

        option.subtract(50);
        option = optionRepository.save(option);

        assertEquals(50, option.getQuantity());
    }
}
