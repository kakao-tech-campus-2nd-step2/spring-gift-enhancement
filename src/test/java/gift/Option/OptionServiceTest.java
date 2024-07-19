package gift.Option;

import gift.domain.category.Category;
import gift.domain.category.JpaCategoryRepository;
import gift.domain.option.JpaOptionRepository;
import gift.domain.option.Option;
import gift.domain.option.OptionService;
import gift.domain.product.JpaProductRepository;
import gift.domain.product.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class OptionServiceTest {

    @Autowired
    private OptionService optionService;
    @Autowired
    private JpaOptionRepository optionRepository;
    @Autowired
    private JpaCategoryRepository categoryRepository;
    @Autowired
    private JpaProductRepository productRepository;

    private Category category;
    private Product product;
    private Option option;

    @BeforeEach
    void setUp() {
        category = categoryRepository.saveAndFlush(
            new Category("에티오피아산", "에티오피아 산 원두를 사용했습니다."));

        product = productRepository.saveAndFlush(new Product("아이스 아메리카노 T", category, 4500,
            "https://example.com/image.jpg"));

        option = optionRepository.saveAndFlush(new Option("에티오피아 커피 옵션1", 289L, product));
    }


    @Test
    @Description("옵션 조회")
    void a
}
