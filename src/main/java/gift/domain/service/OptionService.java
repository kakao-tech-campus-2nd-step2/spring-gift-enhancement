package gift.domain.service;

import gift.domain.dto.request.OptionRequest;
import gift.domain.dto.response.OptionDetailedResponse;
import gift.domain.entity.Option;
import gift.domain.entity.Product;
import gift.domain.exception.conflict.OptionAlreadyExistsInProductException;
import gift.domain.exception.notFound.OptionNotFoundException;
import gift.domain.repository.OptionRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OptionService {

    private final OptionRepository optionRepository;

    public OptionService(OptionRepository optionRepository) {
        this.optionRepository = optionRepository;
    }

    // request가 기존 상품 옵션과 겹치지 않음을 검증하기
    private void validateOptionIsUniqueInProduct(Product product, OptionRequest request) {
        product.getOptions().stream()
            .filter(o -> o.getName().equals(request.name()))
            .findAny()
            .ifPresent(o -> {
                throw new OptionAlreadyExistsInProductException();
            });
    }

    @Transactional(readOnly = true)
    public List<OptionDetailedResponse> getAllOptions() {
        return OptionDetailedResponse.of(optionRepository.findAll());
    }

    @Transactional(readOnly = true)
    public Option getOptionById(Long id) {
        return optionRepository.findById(id).orElseThrow(OptionNotFoundException::new);
    }

    @Transactional
    public Option addOption(Product product, OptionRequest request) {
        // 상품 옵션 중 같은 이름이 있는 경우를 배제하기
        validateOptionIsUniqueInProduct(product, request);

        return optionRepository.save(request.toEntity(product));
    }

    @Transactional
    public Option updateOptionById(Product product, Long optionId, OptionRequest request) {
        //상품 옵션 중 optionId를 가진 옵션이 없는경우 예외
        product.getOptions().stream()
            .filter(o -> o.getId().equals(optionId))
            .findAny()
            .orElseThrow(OptionNotFoundException::new);

        Option option = getOptionById(optionId);
        if (!option.getName().equals(request.name())) {
            //상품 옵션 중 이름이 같은 경우 배제하기
            validateOptionIsUniqueInProduct(product, request);
        }

        option.set(request);
        return option;
    }
}
