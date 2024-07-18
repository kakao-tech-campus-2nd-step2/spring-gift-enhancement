package gift.main.service;

import gift.main.dto.OptionListRequest;
import gift.main.dto.OptionRequest;
import gift.main.dto.ProductRequest;
import gift.main.dto.UserVo;
import gift.main.entity.*;
import gift.main.repository.*;
import jakarta.persistence.EntityManager;
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
    private final OptionListRepository optionListRepository;
    private final OptionRepository optionRepository;
    @Autowired
    private EntityManager entityManager;

    @Autowired
    ProductServiceTest(ProductService productService, UserRepository userRepository, CategoryRepository categoryRepository, WishProductRepository wishProductRepository, ProductRepository productRepository, OptionListRepository optionListRepository, OptionRepository optionRepository) {
        this.productService = productService;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.wishProductRepository = wishProductRepository;
        this.productRepository = productRepository;
        this.optionListRepository = optionListRepository;
        this.optionRepository = optionRepository;
    }


    @Test
    @Transactional
    void saveProductTest() {
        //given
        User seller = new User("seller", "seller@", "1234", "ADMIN");
        userRepository.save(seller);
        entityManager.flush();
        entityManager.clear();

        ProductRequest productRequest = new ProductRequest("testProduct1", 12000, "Url",1);

        List<OptionRequest> options = new ArrayList<>();
        options.add(new OptionRequest("1번", 1));
        options.add(new OptionRequest("2번", 2));
        options.add(new OptionRequest("3번", 3));
        OptionListRequest optionListRequest = new OptionListRequest(options);

        User saveSeller = userRepository.findByEmail("seller@").get();
        UserVo userVo = new UserVo(saveSeller.getId(), saveSeller.getName(), saveSeller.getEmail(), saveSeller.getRole());

        //when
        productService.addProduct(productRequest, optionListRequest, userVo);

        //then
        //어떻게 불러오지?? ⭐ 일단 구현이 먼저다
        /*
        체크해야할 점
            [] 프로덕트 객체가 잘 저장되었는지.
            [] 옵션 리스트가 잘 저장되었는지,
            [] 옵션이 잘 저장되었는지,
         */

        assertThat(productRepository.existsById(1L)).isTrue();
        Product saveProduct = productRepository.findById(1L).get();

        assertThat(optionListRepository.existsByProductId(saveProduct.getId())).isTrue();
        OptionList optionList = optionListRepository.findByProductId(saveProduct.getId()).get();

        assertThat(optionRepository.findAllByOptionListId(optionList.getId()).get().size()).isEqualTo(3);
        System.out.println("optionList.getOptions() = " + optionList.getOptions());
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
        entityManager.flush();
        entityManager.clear();

        User user1 = userRepository.findByEmail("user@").get();
        Product product1 = productRepository.findById(productId).get();
        WishProduct wishProduct = new WishProduct(product1, user1);
        WishProduct saveWishProduct = wishProductRepository.save(wishProduct);
        Long wishProductId = saveWishProduct.getId();
        entityManager.flush();
        entityManager.clear();


        //when
        productService.deleteProduct(productId);
        entityManager.flush();
        entityManager.clear();

        //then
        wishProduct = wishProductRepository.findById(wishProductId).get();
        assertThat(wishProduct.getProduct()).isNull();

    }
}