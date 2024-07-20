package gift.product.business.service;

import gift.product.business.dto.OptionIn;
import gift.product.business.dto.ProductIn;
import gift.product.business.dto.ProductOut;
import gift.product.business.dto.ProductUpdateDto;
import gift.product.persistence.entity.Product;
import gift.product.persistence.repository.CategoryRepository;
import gift.product.persistence.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional(readOnly = true)
    public ProductOut.WithOptions getProduct(Long id) {
        Product product = productRepository.getProductById(id);
        return ProductOut.WithOptions.from(product);
    }

    @Transactional(readOnly = true)
    public ProductOut.Paging getProductsByPage(Pageable pageable) {
        Page<Product> products = productRepository.getProductsByPage(pageable);
        return ProductOut.Paging.from(products);
    }

    @Transactional
    public Long createProduct(ProductIn.Create productInCreate) {
        var category = categoryRepository.getReferencedCategory(productInCreate.categoryId());
        var product = productInCreate.toProduct(category);
        var options = productInCreate.options().stream()
            .map(OptionIn.Create::toOption)
            .toList();
        product.addOptions(options);
        return productRepository.saveProduct(product);
    }

    @Transactional
    public Long updateProduct(ProductIn.Update productInUpdate, Long id) {
        var product = productRepository.getProductById(id);
        var category = categoryRepository.getReferencedCategory(productInUpdate.categoryId());
        product.update(productInUpdate.name(), productInUpdate.description(),
            productInUpdate.price(), productInUpdate.imageUrl());
        product.updateCategory(category);
        return productRepository.saveProduct(product);
    }

    @Transactional
    public Long deleteProduct(Long id) {
        return productRepository.deleteProductById(id);
    }

    @Transactional
    public void deleteProducts(List<Long> productIds) {
        productRepository.deleteProductByIdList(productIds);
    }
}
