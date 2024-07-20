package gift.product.application;

import gift.category.domain.Category;
import gift.category.domain.CategoryRepository;
import gift.exception.type.NotFoundException;
import gift.option.application.OptionResponse;
import gift.option.application.command.OptionCreateCommand;
import gift.option.application.command.OptionUpdateCommand;
import gift.option.domain.Option;
import gift.option.domain.OptionRepository;
import gift.product.application.command.ProductCreateCommand;
import gift.product.application.command.ProductUpdateCommand;
import gift.product.domain.Product;
import gift.product.domain.ProductRepository;
import gift.wishlist.domain.WishlistRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ProductService {
    private final ProductRepository productRepository;
    private final WishlistRepository wishlistRepository;
    private final CategoryRepository categoryRepository;
    private final OptionRepository optionRepository;

    public ProductService(
            ProductRepository productRepository,
            WishlistRepository wishlistRepository,
            CategoryRepository categoryRepository,
            OptionRepository optionRepository
    ) {
        this.productRepository = productRepository;
        this.wishlistRepository = wishlistRepository;
        this.categoryRepository = categoryRepository;
        this.optionRepository = optionRepository;
    }

    public Page<ProductResponse> findAll(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(ProductResponse::from);
    }

    public ProductResponse findById(Long productId) {
        return productRepository.findById(productId)
                .map(ProductResponse::from)
                .orElseThrow(() -> new NotFoundException("해당 상품이 존재하지 않습니다."));
    }

    @Transactional
    public void save(ProductCreateCommand command) {
        Category category = categoryRepository.findById(command.categoryId())
                .orElseThrow(() -> new NotFoundException("해당 카테고리가 존재하지 않습니다."));

        Product product = command.toProduct(category);
        product.validateKakaoInProductName();

        command.optionCreateCommandList().stream()
                .map(OptionCreateCommand::toOption)
                .forEach(product::addOption);

        product.validateHasAtLeastOneOption();

        productRepository.save(product);
    }

    @Transactional
    public void update(ProductUpdateCommand command) {
        Product product = productRepository.findById(command.id())
                .orElseThrow(() -> new NotFoundException("해당 상품이 존재하지 않습니다."));

        Category category = categoryRepository.findById(command.categoryId())
                .orElseThrow(() -> new NotFoundException("해당 카테고리가 존재하지 않습니다."));

        List<Option> newOptions = command.optionUpdateCommandList().stream()
                .map(OptionUpdateCommand::toOption).toList();

        product.update(command, category, newOptions);

        product.validateHasAtLeastOneOption();
        product.validateKakaoInProductName();
    }

    @Transactional
    public void delete(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("해당 상품이 존재하지 않습니다."));

        wishlistRepository.deleteAllByProductId(productId);
        productRepository.delete(product);
    }

    public List<OptionResponse> findOptionsByProductId(Long productId) {
        return optionRepository.findAllByProductId(productId)
                .stream()
                .map(OptionResponse::from)
                .toList();
    }
}
