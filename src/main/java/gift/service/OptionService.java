package gift.service;

import gift.domain.option.Option;
import gift.domain.option.OptionRequest;
import gift.domain.product.Product;
import gift.repository.OptionRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class OptionService {

    private final OptionRepository OptionRepository;

    public OptionService(OptionRepository optionRepository) {
        this.OptionRepository = optionRepository;
    }

    public List<Option> findAllByProductId(Long productId) {
        return OptionRepository.findAllByProduct(new Product(productId));
    }

    @Transactional
    public void save(Long productId, OptionRequest optionRequest) {
        OptionRepository.save(optionRequest.toOption(productId));
    }

    @Transactional
    public void update(Long productId, Long optionId, OptionRequest optionRequest) {
        Option option = OptionRepository.findById(optionId).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 옵션을 찾을 수 없습니다"));
        option.update(optionRequest);
        OptionRepository.save(option);
    }

    @Transactional
    public void subtractQuantity(Long productId, Long optionId, Long quantity) {
        Option option = OptionRepository.findById(optionId).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 옵션을 찾을 수 없습니다"));
        if (option.getQuantity() < quantity) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "상품 수량이 부족합니다");
        }
        option.subtractQuantity(quantity);
        OptionRepository.save(option);
    }

    @Transactional
    public void delete(Long productId, Long optionId) {
        OptionRepository.deleteById(optionId);
    }
}
