package gift.service;


import gift.dto.ProductDto;
import gift.exception.ValueAlreadyExistsException;
import gift.exception.ValueNotFoundException;
import gift.model.product.Category;
import gift.model.product.Product;
import gift.model.product.ProductName;
import gift.repository.CategoryRepository;
import gift.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public void addNewProduct(ProductDto productDto){
        if (productRepository.existsByName(new ProductName(productDto.name()))) {
            throw new ValueAlreadyExistsException("ProductName already exists in Database");
        }
        Category category = findCategory(productDto.categoryName());
        Product product = new Product(category,new ProductName(productDto.name()),productDto.price(),productDto.imageUrl());

        productRepository.save(product);
    }

    public void updateProduct(Long id, ProductDto productDto) {
        Optional<Product> product = productRepository.findById(id);
        if(product.isEmpty()){
            throw new ValueNotFoundException("Product not exists in Database");
        }
        Product updateProduct = product.get();
        Category category = findCategory(productDto.categoryName());

        Product newProduct = new Product(category,new ProductName(productDto.name()),productDto.price(),productDto.imageUrl());
        updateProduct.updateProduct(newProduct);
        productRepository.save(updateProduct);
    }

    public Product selectProduct(Long id) {
        return productRepository.findById(id).get();
    }

    public Page<Product> getAllProducts(Pageable pageable){
        return productRepository.findAll(pageable);
    }

    public void DeleteProduct(Long id){
        productRepository.deleteById(id);
    }

    public Category findCategory(String categoryName) {
        Optional<Category> category = categoryRepository.findByCategoryName(categoryName);
        if (category.isPresent()) {
            return category.get();
        }
        return categoryRepository.save(new Category(categoryName));
    }
}
