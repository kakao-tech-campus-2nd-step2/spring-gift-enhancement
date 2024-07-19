package gift.product.service;

import gift.category.entity.Category;
import gift.product.dto.OptionDto;
import gift.product.dto.ProductDto;
import gift.product.entity.Option;
import gift.product.entity.Product;
import gift.product.repository.OptionRepository;
import gift.product.repository.ProductRepository;
import gift.category.repository.CategoryRepository;
import gift.wish.repository.WishRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

  private final ProductRepository productRepository;
  private final CategoryRepository categoryRepository;
  private final WishRepository wishRepository;
  private final OptionRepository optionRepository;

  @Autowired
  public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository,
      WishRepository wishRepository, OptionRepository optionRepository) {
    this.productRepository = productRepository;
    this.categoryRepository = categoryRepository;
    this.wishRepository = wishRepository;
    this.optionRepository = optionRepository;
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
  public ProductDto addProduct(@Valid ProductDto productDto) {
    try {
      validateProduct(productDto);
      Product product = ProductDto.toEntity(productDto);

      Category category = product.getCategory();
      if (category != null && category.getId() == null) {
        category = categoryRepository.save(category);
        product.setCategory(category);
      }

      for (OptionDto optionDto : productDto.getOptions()) {
        Option option = OptionDto.toEntity(optionDto, product);
        product.addOption(option);
      }

      Product savedProduct = productRepository.save(product);
      return ProductDto.toDto(savedProduct);
    } catch (Exception e) {
      throw new RuntimeException("상품을 추가하는 중에 오류가 발생했습니다.", e);
    }
  }

  @Transactional
  public ProductDto updateProduct(long id, @Valid ProductDto productDto) {
    try {
      validateProduct(productDto);
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

      existingProduct.getOptions().clear();
      for (OptionDto optionDto : productDto.getOptions()) {
        Option option = OptionDto.toEntity(optionDto, existingProduct);
        existingProduct.addOption(option);
      }

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

  public List<OptionDto> getProductOptions(Long productId) {
    try {
      Product product = productRepository.findById(productId)
          .orElseThrow(() -> new RuntimeException("ID가 " + productId + "인 상품을 찾을 수 없습니다."));
      return product.getOptions().stream().map(OptionDto::toDto).collect(Collectors.toList());
    } catch (Exception e) {
      throw new RuntimeException("상품 옵션을 조회하는 중에 오류가 발생했습니다.", e);
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
    if (productDto.getOptions() == null || productDto.getOptions().isEmpty()) {
      throw new IllegalArgumentException("상품에는 항상 하나 이상의 옵션이 있어야 합니다.");
    }

    Set<String> optionNames = new HashSet<>();
    for (OptionDto optionDto : productDto.getOptions()) {
      if (!optionNames.add(optionDto.getName())) {
        throw new IllegalArgumentException("옵션 이름은 중복될 수 없습니다: " + optionDto.getName());
      }
      if (!optionDto.getName().matches("^[\\w\\s\\(\\)\\[\\]\\+\\-&/]{1,50}$")) {
        throw new IllegalArgumentException("옵션 이름 형식이 올바르지 않습니다: " + optionDto.getName());
      }
      if (optionDto.getQuantity() < 1 || optionDto.getQuantity() >= 100000000) {
        throw new IllegalArgumentException("옵션 수량은 1 이상 1억 미만이어야 합니다: " + optionDto.getQuantity());
      }
    }
  }
}
