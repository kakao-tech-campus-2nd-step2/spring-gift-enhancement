package gift.service;

import gift.exception.CustomNotFoundException;
import gift.model.Option;
import gift.model.Product;
import gift.repository.OptionRepository;
import gift.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OptionService {

  private final OptionRepository optionRepository;
  private final ProductRepository productRepository;

  @Autowired
  public OptionService(OptionRepository optionRepository, ProductRepository productRepository) {
    this.optionRepository = optionRepository;
    this.productRepository = productRepository;
  }

  public List<Option> getOptionsByProductId(Long productId) {
    return optionRepository.findByProductId(productId);
  }

  public Option addOptionToProduct(Long productId, Option option) {
    if (optionRepository.existsByNameAndProductId(option.getName(), productId)) {
      throw new IllegalArgumentException("동일한 상품 내의 옵션 이름은 중복될 수 없습니다.");
    }

    Product product = productRepository.findById(productId)
            .orElseThrow(() -> new CustomNotFoundException("Product not found"));

    option.setProduct(product);
    return optionRepository.save(option);
  }
}
