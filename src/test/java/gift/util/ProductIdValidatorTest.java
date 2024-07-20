package gift.util;

import gift.entity.Product;
import gift.entity.ProductDTO;
import gift.entity.WishlistDTO;
import gift.service.ProductService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class ProductIdValidatorTest {

    @Autowired
    private Validator validator;

    @Autowired
    private ProductService productService;

    @Test
    public void save_existingProductId() {
        //given
        ProductDTO product = new ProductDTO("abc", 123, "www.test.com", 1L);
        Product savedProduct = productService.save(product);

        WishlistDTO wishList = new WishlistDTO(savedProduct.getId());

        //when
        Set<ConstraintViolation<WishlistDTO>> violations = validator.validate(wishList);

        //then
        assertThat(violations).isEmpty();
    }

    @Test
    public void save_nonexistentProductId() {
        //given
        ProductDTO product = new ProductDTO("abc", 123, "www.test.com", 1L);
        Product savedProduct = productService.save(product);

        WishlistDTO wishList = new WishlistDTO(savedProduct.getId() + 1);

        //when
        Set<ConstraintViolation<WishlistDTO>> violations = validator.validate(wishList);

        //then
        assertThat(violations).isNotEmpty();
    }
}
