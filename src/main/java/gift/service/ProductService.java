package gift.service;

import gift.entity.Category;
import gift.entity.Product;
import gift.domain.ProductDTO;
import gift.repository.CategoryRepository;
import gift.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public Page<Product> getAllProduct(int page, int size) {
        Pageable pageRequest = createPageRequestUsing(page, size);

        int start = (int) pageRequest.getOffset();
        int end = start + pageRequest.getPageSize();
        if (page > 0) { start += 1; }

        List<Product> pageContent = productRepository.findByProductIdBetween(start, end);
        return new PageImpl<>(pageContent, pageRequest, pageContent.size());
    }

    private Pageable createPageRequestUsing(int page, int size) {
        return PageRequest.of(page, size);
    }

    public Product getProductById(int id) {
        try {
            return productRepository.findById(id);
        }
        catch (NoSuchElementException e) {
            throw new NoSuchElementException();
        }
    }

    public Product addProduct(ProductDTO productDTO) {
        try {
            var category = categoryRepository.findById(productDTO.categoryId());
            Product product = new Product(category, productDTO.price(), productDTO.name(), productDTO.imgURL());

            return productRepository.save(product);
        }
        catch (Exception e) {
            throw new IllegalArgumentException();
        }
    }

    public Product updateProduct(int id, ProductDTO productDTO) {
        try {
            var category = categoryRepository.findById(productDTO.categoryId());
            Product product = new Product(id, category, productDTO.price(), productDTO.name(), productDTO.imgURL());

            return productRepository.save(product);
        }
        catch (NoSuchElementException e) {
            throw new NoSuchElementException("No product found with id " + id);
        }
        catch (Exception e) {
            throw new IllegalArgumentException();
        }
    }

    public void deleteProduct(int id) {
        try {
            productRepository.deleteById(id);
        }
        catch (Exception e) {
            throw new NoSuchElementException();
        }
    }
}
