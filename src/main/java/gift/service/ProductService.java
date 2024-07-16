package gift.service;

import gift.dto.PageRequestDTO;
import gift.dto.InputProductDTO;
import gift.model.Category;
import gift.model.Product;
import gift.repository.CategoryRepository;
import gift.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    //전체 조회
    public Page<Product> getAllProducts(PageRequestDTO pageRequestDTO){
        Pageable pageable = PageRequest.of(pageRequestDTO.getPage(),
                pageRequestDTO.getSize(), pageRequestDTO.getSort());

        return productRepository.findAll(pageable);
    }

    //하나 조회
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 상품이 없습니다."));
    }

    //저장
    public void saveProduct(InputProductDTO inputProductDTO) {
        Category category = categoryRepository.findByName(inputProductDTO.getCategory())
                .orElseThrow(() -> new NoSuchElementException("해당 카테고리가 없습니다."));
        Product product = new Product(
                inputProductDTO.getName(),
                inputProductDTO.getPrice(),
                inputProductDTO.getImageUrl(),
                category
                );
        productRepository.save(product);
    }

    //삭제
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public void updateProduct(Long id, InputProductDTO inputProductDTO) {
        System.out.println(inputProductDTO);
        Product oldProduct = productRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 상품이 없습니다."));
        Category category = null;
        if (inputProductDTO.getCategory() != null) {
            category = categoryRepository.findByName(inputProductDTO.getCategory())
                    .orElseThrow(() -> new NoSuchElementException("해당 카테고리가 없습니다."));
        }
        Product updatedProduct = oldProduct.update(
                inputProductDTO.getName(),
                inputProductDTO.getPrice(),
                inputProductDTO.getImageUrl(),
                category);
        productRepository.save(updatedProduct);
    }

    public int getPreviousPage(Page<Product> productPage) {
        if (productPage.hasPrevious()) {
            Pageable previousPageable = productPage.previousPageable();
            return previousPageable.getPageNumber();
        }
        return -1;
    }

    public int getNextPage(Page<Product> productPage) {
        if (productPage.hasNext()) {
            Pageable nextPageable = productPage.nextPageable();
            return nextPageable.getPageNumber();
        }
        return -1;
    }
}