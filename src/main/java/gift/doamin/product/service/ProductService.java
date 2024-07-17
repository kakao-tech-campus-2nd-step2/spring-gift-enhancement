package gift.doamin.product.service;

import gift.doamin.category.entity.Category;
import gift.doamin.category.exception.CategoryNotFoundException;
import gift.doamin.category.repository.JpaCategoryRepository;
import gift.doamin.product.dto.ProductForm;
import gift.doamin.product.dto.ProductParam;
import gift.doamin.product.entity.Product;
import gift.doamin.product.exception.NotEnoughAutorityException;
import gift.doamin.product.exception.ProductNotFoundException;
import gift.doamin.product.repository.JpaProductRepository;
import gift.doamin.user.entity.User;
import gift.doamin.user.exception.UserNotFoundException;
import gift.doamin.user.repository.JpaUserRepository;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final JpaProductRepository productRepository;
    private final JpaUserRepository userRepository;
    private final JpaCategoryRepository categoryRepository;

    public ProductService(JpaProductRepository productRepository,
        JpaUserRepository userRepository, JpaCategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    public ProductParam create(ProductForm productForm) {
        if (productForm.getName().contains("카카오")) {
            throw new NotEnoughAutorityException("'카카오'가 포함된 문구는 담당 MD와 협의한 경우에만 사용할 수 있습니다.");
        }

        User user = userRepository.findById(productForm.getUserId())
            .orElseThrow(UserNotFoundException::new);

        Category category = categoryRepository.findById(productForm.getCategory_id())
            .orElseThrow(CategoryNotFoundException::new);

        Product product = productRepository.save(
            new Product(user, category, productForm.getName(),
                productForm.getPrice(), productForm.getImageUrl()));

        return new ProductParam(product);
    }

    public Page<ProductParam> getPage(int pageNum) {

        Pageable pageable = PageRequest.of(pageNum, 5);
        return productRepository.findAll(pageable).map(ProductParam::new);
    }

    public ProductParam readOne(Long id) {

        Product product = productRepository.findById(id).orElseThrow(ProductNotFoundException::new);

        return new ProductParam(product);
    }

    public ProductParam update(Long id, ProductForm productForm, boolean isSeller) {

        Optional<Product> target = productRepository.findById(id);
        if (target.isEmpty()) {
            return create(productForm);
        }

        Product product = target.get();

        checkAuthority(productForm.getUserId(), product, isSeller);

        Category category = categoryRepository.findById(productForm.getCategory_id())
            .orElseThrow(CategoryNotFoundException::new);
        product.updateAll(productForm, category);
        return new ProductParam(productRepository.save(product));
    }

    public void delete(Long userId, Long id, boolean isSeller) {
        Product target = productRepository.findById(id).orElseThrow(ProductNotFoundException::new);

        checkAuthority(userId, target, isSeller);

        productRepository.deleteById(id);
    }

    private void checkAuthority(Long userId, Product target, boolean isSeller) {
        if (isSeller && !target.getUser().getId().equals(userId)) {
            throw new NotEnoughAutorityException();
        }

    }
}
