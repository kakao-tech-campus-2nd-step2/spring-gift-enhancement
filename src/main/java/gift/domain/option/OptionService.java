package gift.domain.option;

import gift.domain.option.dto.OptionRequestDTO;
import gift.domain.product.JpaProductRepository;
import gift.domain.product.Product;
import gift.global.exception.BusinessException;
import gift.global.exception.ErrorCode;

import gift.global.exception.option.OptionDuplicateException;
import gift.global.exception.option.OptionNotFoundException;
import gift.global.exception.product.ProductNotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
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

    // 상품 옵션 수량 차감
    @Transactional
    public void decreaseOptionQuantity(Long productId, Long optionId, Long quantity) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new ProductNotFoundException(productId));
        Option option = optionRepository.findById(optionId)
            .orElseThrow(() -> new OptionNotFoundException(optionId));

        if (option.getQuantity() < quantity || quantity < 0) {
            throw new BusinessException(ErrorCode.BAD_REQUEST, "차감할 수량의 값이 올바르지 않습니다.");
        }

        if (option.getQuantity() == quantity) {
            // 상품에 옵션이 1개밖에 없을 때 - 옵션, 해당 옵션의 상품 모두 삭제
            if (product.getOptions().size() == 1) {
                productRepository.deleteById(productId); // Cascade 로 옵션도 삭제됨
                return;
            }
            // 상품에 옵션이 2개 이상일 때 - 옵션 삭제
            optionRepository.deleteById(optionId);
            return;
        }
        // 수량만 차감
        option.decrease(quantity);
    }
}
