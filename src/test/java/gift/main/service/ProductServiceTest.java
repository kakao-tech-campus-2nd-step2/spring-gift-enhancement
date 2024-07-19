package gift.main.service;

import gift.main.dto.OptionListRequest;
import gift.main.dto.OptionRequest;
import gift.main.dto.ProductRequest;
import gift.main.dto.UserVo;
import gift.main.entity.Category;
import gift.main.entity.Product;
import gift.main.entity.User;
import gift.main.entity.WishProduct;
import gift.main.repository.*;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ProductServiceTest {

    private final ProductService productService;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final WishProductRepository wishProductRepository;
    private final ProductRepository productRepository;
    private final OptionRepository optionRepository;
    private final EntityManager entityManager;

    @Autowired
    public ProductServiceTest(
            ProductService productService,
            UserRepository userRepository,
            CategoryRepository categoryRepository,
            WishProductRepository wishProductRepository,
            ProductRepository productRepository,
            OptionRepository optionRepository,
            EntityManager entityManager) {

        this.productService = productService;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.wishProductRepository = wishProductRepository;
        this.productRepository = productRepository;
        this.optionRepository = optionRepository;
        this.entityManager = entityManager;
    }

    @Test
    @Transactional
    @DisplayName("옵션이_정확하게_들어가는지")
    void saveProductTest() {
        //given
        User seller = new User("seller", "seller@", "1234", "ADMIN");
        User saveSeller = userRepository.save(seller);
        UserVo userVo = new UserVo(saveSeller.getId(), saveSeller.getName(), saveSeller.getEmail(), saveSeller.getRole());
        entityManager.flush();
        entityManager.clear();

        ProductRequest productRequest = new ProductRequest("testProduct1", 12000, "Url", 1);

        List<OptionRequest> options = new ArrayList<>();
        options.add(new OptionRequest("1번", 1));
        options.add(new OptionRequest("2번", 2));
        options.add(new OptionRequest("3번", 3));
        OptionListRequest optionListRequest = new OptionListRequest(options);

        //when
        productService.registerProduct(productRequest, optionListRequest, userVo);
        entityManager.clear(); //이 부분 주석 처리하면 마지막 절에서 null예외가 발생해 테스트 코드 실패합니다.

        //then
        assertThat(productRepository.existsById(1L)).isTrue();
        Product saveProduct = productRepository.findById(1L).get();

        assertThat(optionRepository.findAllByProductId(saveProduct.getId()).get().size()).isEqualTo(3);

    }

    @Test
    void deleteProductTest() {
        //given
        User user = new User("user", "user@", "1234", "ADMIN");
        User seller = new User("seller", "seller@", "1234", "ADMIN");
        userRepository.save(user);
        userRepository.save(seller);


        Category category = categoryRepository.findByName("패션").get();
        User seller1 = userRepository.findByEmail("seller@").get();
        Product product = new Product("testProduct1", 12000, "Url", seller1, category);
        Product saveProduct = productRepository.save(product);
        Long productId = saveProduct.getId();

        User user1 = userRepository.findByEmail("user@").get();
        Product product1 = productRepository.findById(productId).get();
        WishProduct wishProduct = new WishProduct(product1, user1);
        WishProduct saveWishProduct = wishProductRepository.save(wishProduct);
        Long wishProductId = saveWishProduct.getId();


        //when
        productService.deleteProduct(productId);
        entityManager.flush();
        entityManager.clear();

        //then
        wishProduct = wishProductRepository.findById(wishProductId).get();
        assertThat(wishProduct.getProduct()).isNull();

    }
}