//package gift;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.Mockito.when;
//
//import gift.model.Category;
//import gift.model.ProductDTO;
//import gift.repository.CategoryRepository;
//import gift.service.OptionService;
//import gift.service.ProductService;
//import java.util.Optional;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//
//@SpringBootTest
//public class ProductServiceTest {
//
//    @Autowired
//    ProductService productService;
//    @Autowired
//    OptionService optionService;
//    @MockBean
//    CategoryRepository categories;
//
//    @Test
//    void findDefaultOption() {
//        var mockCategory = new Category(1L, "category", "color", "image-url", "");
//        when(categories.findById(1L)).thenReturn(Optional.of(mockCategory));
//        var productDTO = new ProductDTO(1L, "product", 4500L, "image-url", 1L);
//        Pageable pageable = PageRequest.of(0, 10);
//
//        productService.createProduct(productDTO);
//
//        assertThat(optionService.getOptions(productDTO.id(), pageable)).isNotEmpty();
//    }
//
//}
