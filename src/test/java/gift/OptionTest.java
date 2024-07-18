package gift;

import gift.model.Option;
import gift.model.Product;
import gift.model.ProductDto;
import gift.repository.OptionRepository;
import gift.repository.ProductRepository;
import gift.service.OptionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OptionTest {

  private OptionRepository optionRepository;
  private ProductRepository productRepository;
  private OptionService optionService;

  @BeforeEach
  void setUp() {
    optionRepository = mock(OptionRepository.class);
    productRepository = mock(ProductRepository.class);
    optionService = new OptionService(optionRepository, productRepository);
  }
  @Test
  void testOptionCreation() {
    Product product = new Product("Test Product", 1000, "test.png", null);
    Option option = new Option("Option 1", 10, product);

    assertNotNull(option);
    assertEquals("Option 1", option.getName());
    assertEquals(10, option.getQuantity());
    assertEquals(product, option.getProduct());
  }

  @Test
  void testOptionCreationWithInvalidName() {
    Product product = new Product("Test Product", 1000, "test.png", null);

    assertThrows(IllegalArgumentException.class, () -> new Option("", 10, product));
    assertThrows(IllegalArgumentException.class, () -> new Option("test".repeat(50), 10, product));
    assertThrows(IllegalArgumentException.class, () -> new Option("Invalid@Name", 10, product));
  }

  @Test
  void testOptionCreationWithInvalidQuantity() {
    Product product = new Product("Test Product", 1000, "test.png", null);

    assertThrows(IllegalArgumentException.class, () -> new Option("Option 1", 0, product));
    assertThrows(IllegalArgumentException.class, () -> new Option("Option 1", 100_000_000, product));
  }
}

