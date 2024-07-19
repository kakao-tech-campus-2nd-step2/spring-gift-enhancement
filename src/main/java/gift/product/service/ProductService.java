package gift.product.service;

import gift.category.entity.Category;
import gift.product.dto.ProductDto;
import gift.product.entity.Product;
import gift.product.repository.ProductRepository;
import gift.category.repository.CategoryRepository;
import gift.wish.repository.WishRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class ProductService {

  private final ProductRepository productRepository;
  private final CategoryRepository categoryRepository;
  private final WishRepository wishRepository;

  @Autowired
  public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository,
      WishRepository wishRepository) {
    this.productRepository = productRepository;
    this.categoryRepository = categoryRepository;
    this.wishRepository = wishRepository;
  }

  public Page<ProductDto> findAll(Pageable pageable) {
    try {
      return productRepository.findAll(pageable).map(ProductDto::toDto);
    } catch (Exception e) {
      throw new RuntimeException("모든 상품을 조회하는 중에 오류가 발생했습니다.", e);
    }
  }

  public ProductDto getProductById(long id) {
    try {
      return productRepository.findById(id)
          .map(ProductDto::toDto)
          .orElseThrow(() -> new RuntimeException("ID가 " + id + "인 상품을 찾을 수 없습니다."));
    } catch (Exception e) {
      throw new RuntimeException("ID가 " + id + "인 상품을 조회하는 중에 오류가 발생했습니다.", e);
    }
  }

  @Transactional
  public ProductDto addProduct(ProductDto productDto) {
    try {
      Product product = ProductDto.toEntity(productDto);
      validateProduct(productDto);

      Category category = product.getCategory();
      if (category != null && category.getId() == null) {
        category = categoryRepository.save(category);
        product.setCategory(category);
      }

      Product savedProduct = productRepository.save(product);
      return ProductDto.toDto(savedProduct);
    } catch (Exception e) {
      throw new RuntimeException("상품을 추가하는 중에 오류가 발생했습니다.", e);
    }
  }

  @Transactional
  public ProductDto updateProduct(long id, ProductDto productDto) {
    try {
      Product existingProduct = productRepository.findById(id)
          .orElseThrow(() -> new RuntimeException("ID가 " + id + "인 상품이 존재하지 않습니다."));
      existingProduct.setName(productDto.getName());
      existingProduct.setPrice(productDto.getPrice());
      existingProduct.setImageUrl(productDto.getImageUrl());

      Category category = productDto.getCategory();
      if (category != null) {
        if (category.getId() == null) {
          category = categoryRepository.save(category);
        }
        existingProduct.setCategory(category);
      }

      validateProduct(productDto);
      Product savedProduct = productRepository.save(existingProduct);
      return ProductDto.toDto(savedProduct);
    } catch (Exception e) {
      throw new RuntimeException("상품을 업데이트하는 중에 오류가 발생했습니다.", e);
    }
  }

  public void deleteProduct(long id) {
    try {
      wishRepository.deleteAllByProductId(id);
      productRepository.deleteById(id);
    } catch (Exception e) {
      throw new RuntimeException("ID가 " + id + "인 상품을 삭제하는 중에 오류가 발생했습니다.", e);
    }
  }

  private void validateProduct(ProductDto productDto) {
    if (productDto.getName() == null || productDto.getName().trim().isEmpty()) {
      throw new IllegalArgumentException("상품 이름은 비어 있을 수 없습니다.");
    }
    if (productDto.getPrice() <= 0) {
      throw new IllegalArgumentException("상품 가격은 0보다 커야 합니다.");
    }
    if (productDto.getImageUrl() == null || productDto.getImageUrl().trim().isEmpty()) {
      throw new IllegalArgumentException("상품 이미지 URL은 비어 있을 수 없습니다.");
    }
    if (productDto.getCategory() == null) {
      throw new IllegalArgumentException("상품 카테고리는 비어 있을 수 없습니다.");
    }
  }
}
