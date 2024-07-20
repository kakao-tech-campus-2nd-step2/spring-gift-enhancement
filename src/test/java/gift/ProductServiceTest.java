package gift;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import gift.dto.ProductResponseDto;
import gift.entity.Category;
import gift.entity.Option;
import gift.entity.Product;
import gift.exception.ProductException;
import gift.repository.ProductRepository;
import gift.service.OptionService;
import gift.service.ProductService;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private OptionService optionService;

    @InjectMocks
    private ProductService productService;

    @Test
    @DisplayName("상품 추가 테스트")
    public void addProductTest(){
        Product product = new Product("치킨", 20000, "chicken.com", new Category("음식"));
        when(productRepository.save(any(Product.class))).thenReturn(product);
        when(productRepository.existsByName(anyString())).thenReturn(false);

        Long id = productService.addProduct(product);

        verify(productRepository, times(1)).save(product);

    }

    @Test
    @DisplayName("중복 이름 상품 추가 테스트")
    public void addProductTest2(){
        Product product = new Product("치킨", 20000, "chicken.com", new Category("음식"));
        when(productRepository.existsByName(anyString())).thenReturn(true);

        Long id = productService.addProduct(product);

        assertThat(id).isEqualTo(-1L);
        verify(productRepository, never()).save(product);
    }

    @Test
    @DisplayName("전건 조회 테스트")
    public void  findAllTest(){
        Product product = new Product("치킨", 20000, "chicken.com", new Category("음식"));
        when(productRepository.findAll()).thenReturn(Arrays.asList(product));

        List<ProductResponseDto> products = productService.findAll();

        assertThat(products).hasSize(1);
        assertThat(products.get(0).getName()).isEqualTo("치킨");
    }

    @Test
    @DisplayName("1개이상 옵션 필수등록 테스트")
    public void noOptionTest(){
        Product product = new Product("치킨", 20000, "chicken.com", new Category("음식"));

        ProductException exception = assertThrows(ProductException.class, () -> {
            productService.save(product);
        });

        assertThat("상품에는 최소 하나 이상의 옵션이 있어야합니다.").isEqualTo(exception.getMessage());

    }

    @Test
    @DisplayName("중복 옵션 테스트")
    public void duplicateOptionTest(){
        Product product = new Product("치킨", 20000, "chicken.com", new Category("음식"));
        product.addOption(new Option("후라이드", 1, product));
        product.addOption(new Option("후라이드", 2, product));

        ProductException exception = assertThrows(ProductException.class, () -> {
            productService.save(product);
        });

        assertThat("동일한 상품 내에 중복된 옵션이 있습니다.").isEqualTo(exception.getMessage());

    }





}
