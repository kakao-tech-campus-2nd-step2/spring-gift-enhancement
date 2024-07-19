package gift.domain.option;

import gift.domain.option.dto.OptionRequestDTO;
import gift.domain.product.JpaProductRepository;
import gift.domain.product.Product;
import gift.global.exception.BusinessException;
import gift.global.exception.option.OptionDuplicateException;
import gift.global.exception.option.OptionNotFoundException;
import gift.global.exception.product.ProductNotFoundException;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class OptionService {

    private final JpaOptionRepository optionRepository;
    private final JpaProductRepository productRepository;

    public OptionService(JpaOptionRepository optionRepository,
        JpaProductRepository productRepository) {
        this.optionRepository = optionRepository;
        this.productRepository = productRepository;
    }

    public List<Option> getOptions() {
        List<Option> options = optionRepository.findAll();
        return options;
    }

    public List<Option> getOptionsByProductId(Long productId) {
        List<Option> options = optionRepository.findAllByProductId(productId);
        return options;
    }

    // 기존 상품에 옵션 추가
    public void addOption(Long productId, OptionRequestDTO optionRequestDTO) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new ProductNotFoundException(productId));

        List<Option> options = optionRepository.findAllByProductId(productId);

        for (Option option : options) {
            if (option.getName().equals(optionRequestDTO.getName())) {
                throw new OptionDuplicateException(optionRequestDTO.getName());
            }
        }

        Option option = new Option(optionRequestDTO.getName(), optionRequestDTO.getQuantity(),
            product);

        optionRepository.save(option);
    }

    // 상품 생성 시 옵션 입력
    public void addOption(Product product, OptionRequestDTO optionRequestDTO) {
        List<Option> options = optionRepository.findAllByProduct(product);

        Option option = new Option(optionRequestDTO.getName(), optionRequestDTO.getQuantity(),
            product);

        optionRepository.save(option);
    }

    // 옵션 수정
    public void updateOption(Long productId, Long optionId, OptionRequestDTO optionRequestDTO) {
        productRepository.findById(productId)
            .orElseThrow(() -> new ProductNotFoundException(productId));

        List<Option> options = optionRepository.findAllByProductId(productId);

        for (Option option : options) {
            if (option.getName().equals(optionRequestDTO.getName()) && option.getId() != optionId) {
                throw new OptionDuplicateException(optionRequestDTO.getName());
            }
        }

        Option option = optionRepository.findById(optionId)
            .orElseThrow(() -> new OptionNotFoundException(optionId));

        option.update(optionRequestDTO.getName(), optionRequestDTO.getQuantity());

        optionRepository.save(option);
    }
}
