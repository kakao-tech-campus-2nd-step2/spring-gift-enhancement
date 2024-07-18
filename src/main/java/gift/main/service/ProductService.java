package gift.main.service;

import gift.main.Exception.CustomException;
import gift.main.Exception.ErrorCode;
import gift.main.dto.*;
import gift.main.entity.*;
import gift.main.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final OptionRepository optionRepository;
    private final OptionListRepository optionListRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final WishProductRepository wishProductRepository;

    public ProductService(ProductRepository productRepository, OptionRepository optionRepository, OptionListRepository optionListRepository, UserRepository userRepository, CategoryRepository categoryRepository, WishProductRepository wishProductRepository) {
        this.productRepository = productRepository;
        this.optionRepository = optionRepository;
        this.optionListRepository = optionListRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;

        this.wishProductRepository = wishProductRepository;
    }
    public Page<ProductResponce> getProductPage(Pageable pageable) {
        Page<ProductResponce> productPage = productRepository.findAll(pageable)
                .map(ProductResponce::new);
        return productPage;
    }

    @Transactional
    public void addProduct(ProductRequest productRequest, OptionListRequest optionListRequest, UserVo user) {
        User seller = userRepository.findById(user.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        Category category = categoryRepository.findByUniNumber(productRequest.categoryUniNumber())
                .orElseThrow(() -> new CustomException(ErrorCode.CATEGORY_NOT_FOUND));

        Product product = new Product(productRequest, seller, category);
        Product savedProduct = productRepository.save(product);

        // OptionList 객체 생성 및 저장
        OptionList optionList = new OptionList(savedProduct.getId(), optionListRequest.getSize());
        optionListRepository.save(optionList);

        // Option 객체 생성 및 저장
        List<OptionRequest> optionRequestList = optionListRequest.getOptionRequestList();
        optionRequestList.stream()
                .map(optionRequest -> new Option(optionRequest.name(), optionRequest.num(), optionList))
                .forEach(option -> optionRepository.save(option));

        // JPA 세션 내에서 OptionList의 options 필드를 초기화하여 접근
        OptionList reloadedOptionList = optionListRepository.findById(optionList.getId()).orElseThrow(() -> new CustomException(ErrorCode.EXISTS_PRODUCT));
        System.out.println("reloadedOptionList.getOptions() = " + reloadedOptionList.getOptions());
        }



    @Transactional
    public void deleteProduct(long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));
        product.getWishProducts().stream()
                .forEach((wishProduct) -> {
                    wishProduct.setProductIdToNull();
                    wishProductRepository.save(wishProduct);
                });
        productRepository.deleteById(id);
    }

    @Transactional
    public void updateProduct(long id, ProductRequest productRequest) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));
        Category category = categoryRepository.findByUniNumber(productRequest.categoryUniNumber())
                .orElseThrow(() -> new CustomException(ErrorCode.CATEGORY_NOT_FOUND));
        product.updateValue(productRequest, category);
        productRepository.save(product);
    }


    public ProductResponce getProduct(long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));
        return new ProductResponce(product);
    }


}

