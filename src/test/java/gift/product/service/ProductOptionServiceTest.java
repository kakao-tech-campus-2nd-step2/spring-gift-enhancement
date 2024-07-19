package gift.product.service;

import static java.util.Optional.of;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import gift.product.domain.Category;
import gift.product.domain.Product;
import gift.product.domain.ProductOption;
import gift.product.persistence.ProductOptionRepository;
import gift.product.persistence.ProductRepository;
import gift.product.service.command.ProductOptionCommand;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductOptionServiceTest {
    @Mock
    private ProductRepository productRepository;
    @Mock
    private ProductOptionRepository productOptionRepository;

    @InjectMocks
    private ProductOptionService productOptionService;

    @Test
    @DisplayName("ProductOptionService Option생성 테스트")
    void createProductOptionTest() {
        //given
        final Long productId = 1L;
        ProductOptionCommand productOptionCommand = new ProductOptionCommand("optionName", 10);

        Category category = new Category("카테고리", "색상", "이미지", "설명");
        Product product = new Product("productName", 1000, "이미지", category);
        ProductOption productOption = new ProductOption(1L, "optionName", 10, product);

        given(productRepository.findById(any())).willReturn(of(product));
        given(productOptionRepository.save(any())).willReturn(productOption);

        //when
        Long optionId = productOptionService.createProductOption(productId, productOptionCommand);

        //then
        assertThat(optionId).isEqualTo(productOption.getId());
    }

    @Test
    @DisplayName("ProductOptionService Option수정 테스트")
    void modifyProductOptionTest() {
        //given
        final Long productId = 1L;
        final Long optionId = 1L;
        ProductOptionCommand productOptionCommand = new ProductOptionCommand("newName", 10);

        Category category = new Category("카테고리", "색상", "이미지", "설명");
        Product product = new Product("productName", 1000, "이미지", category);
        ProductOption productOption = new ProductOption(1L, "optionName", 10, product);

        given(productOptionRepository.findByProductIdAndId(any(), any())).willReturn(of(productOption));

        //when
        productOptionService.modifyProductOption(productId, optionId, productOptionCommand);

        //then
        assertThat(productOption.getName()).isEqualTo(productOptionCommand.name());
        assertThat(productOption.getQuantity()).isEqualTo(productOptionCommand.quantity());
    }

}