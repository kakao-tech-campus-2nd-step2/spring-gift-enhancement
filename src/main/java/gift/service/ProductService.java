package gift.service;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import gift.dto.ProductDto;
import gift.dto.response.ProductPageResponse;
import gift.entity.Category;
import gift.entity.Product;
import gift.entity.WishList;
import gift.exception.CustomException;
import gift.repository.CategoryRepository;
import gift.repository.ProductRepository;
import gift.repository.WishListRepository;
import jakarta.transaction.Transactional;

@Service
public class ProductService{

    private ProductRepository productRepository;
    private WishListRepository wishListRepository;
    private CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, WishListRepository wishListRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.wishListRepository = wishListRepository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public ProductPageResponse getPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        ProductPageResponse productPageResponse = new ProductPageResponse();
        productPageResponse.fromPage(productRepository.findByOrderByNameDesc(pageable));
        return productPageResponse;
    }

    @Transactional
    public ProductDto findById(Long id){
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new CustomException("Product with id " + id + " not found", HttpStatus.NOT_FOUND));
        return new ProductDto(product.getId(), product.getName(), product.getPrice(), product.getImageUrl(), product.getCategory().getName());
    }

    @Transactional
    public void addProduct(ProductDto productDto) {
        Category category = categoryRepository.findByName(productDto.getCategory())
                .orElseThrow(() -> new CustomException("Category with name " + productDto.getCategory() + " not found", HttpStatus.NOT_FOUND));
            
        if(productRepository.findById(productDto.getId()).isEmpty()){
            Product product = productDto.toEntity(category);
            productRepository.save(product);
        }else{
            throw new CustomException("Product with id " + productDto.getId() + "exists", HttpStatus.CONFLICT);
        }
    }

    @Transactional
    public void updateProduct(ProductDto productDto) {

        Optional<Product> optionalProduct = productRepository.findById(productDto.getId());

        Category category = categoryRepository.findByName(productDto.getCategory())
                .orElseThrow(() -> new CustomException("Category with name " + productDto.getCategory() + " not found", HttpStatus.NOT_FOUND));

        if (optionalProduct.isPresent()) {
            Product product = productDto.toEntity(category);
            productRepository.delete(optionalProduct.get());
            productRepository.save(product);
        }else{
            throw new CustomException("Product with id " + productDto.getId() + " not found", HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    public void deleteProduct(Long id) {

        productRepository.findById(id)
            .orElseThrow(() -> new CustomException("Product with id " + id + " not found", HttpStatus.NOT_FOUND));

        List<WishList> wishList = wishListRepository.findByProductId(id);
        wishListRepository.deleteAll(wishList);

        productRepository.deleteById(id);
    }
}