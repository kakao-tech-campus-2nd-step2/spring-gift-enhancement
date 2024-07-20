package gift.domain.service;

import gift.domain.dto.request.OptionRequest;
import gift.domain.dto.response.OptionDetailedResponse;
import gift.domain.entity.Option;
import gift.domain.entity.Product;
import gift.domain.exception.conflict.OptionAlreadyExistsInProductException;
import gift.domain.exception.notFound.OptionNotFoundException;
import gift.domain.repository.OptionRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OptionService {

    private final OptionRepository optionRepository;

    public OptionService(OptionRepository optionRepository) {
        this.optionRepository = optionRepository;
    }

    // request가 기존 상품 옵션들과 겹치지 않음을 검증하기
    private void validateOptionIsUniqueInProduct(Product product, OptionRequest request) {
        Objects.requireNonNullElse(product.getOptions(), new ArrayList<Option>()).stream()
            .filter(o -> o.getName().equals(request.name()))
            .findAny()
            .ifPresent(o -> {
                throw new OptionAlreadyExistsInProductException();
            });
    }

    // 옵션이 상품에 포함되어 있음을 검증하기
    private void validateOptionIsInProduct(Product product, Long optionId) {
        product.getOptions().stream()
            .filter(o -> o.getId().equals(optionId))
            .findAny()
            .orElseThrow(OptionNotFoundException::new);
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
        validateOptionIsUniqueInProduct(product, request);
        return optionRepository.save(request.toEntity(product));
    }

    @Transactional
    public List<Option> addOptions(Product product, List<OptionRequest> request) {
        request.forEach(req -> {
            if (request.stream().filter(req2 -> req2.name().equals(req.name())).count() != 1) {
                throw new OptionAlreadyExistsInProductException();
            }
            optionRepository.save(req.toEntity(product));
        });
        return optionRepository.findAllByProduct(product);
    }

    @Transactional
    public Option updateOptionById(Product product, Long optionId, OptionRequest request) {
        validateOptionIsInProduct(product, optionId);
        Option option = getOptionById(optionId);
        if (!option.getName().equals(request.name())) {
            validateOptionIsUniqueInProduct(product, request);
        }

        option.set(request);
        return option;
    }

    @Transactional
    public void deleteOptionById(Product product, Long optionId) {
        validateOptionIsInProduct(product, optionId);
        Option option = getOptionById(optionId);
        optionRepository.delete(option);
    }
}
