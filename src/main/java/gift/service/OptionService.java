package gift.service;

import gift.domain.option.Option;
import gift.domain.option.OptionRepository;
import gift.domain.product.Product;
import gift.domain.product.ProductRepository;
import gift.dto.OptionRequestDto;
import gift.dto.OptionResponseDto;
import gift.exception.CustomException;
import gift.exception.ErrorCode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OptionService {
    private final ProductRepository productRepository;
    private final OptionRepository optionRepository;

    public OptionService(ProductRepository productRepository, OptionRepository optionRepository) {
        this.productRepository = productRepository;
        this.optionRepository = optionRepository;
    }

    public List<OptionResponseDto> getOptions(long productId) {
        Product product = getProduct(productId);
        return product.getOptions().stream().map(OptionResponseDto::new).toList();
    }


    public void saveOption(Long id, OptionRequestDto request) {
        Product product = getProduct(id);
        Option option = new Option(request.getName(), request.getQuantity(), product);
        product.addOption(option);
        optionRepository.save(option);
    }

    private Product getProduct(long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_PRODUCT, productId));
    }
}
