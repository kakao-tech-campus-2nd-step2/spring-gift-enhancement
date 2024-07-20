package gift.domain.service;

import gift.domain.dto.request.OptionAddRequest;
import gift.domain.dto.request.OptionUpdateRequest;
import gift.domain.dto.response.OptionDetailedResponse;
import gift.domain.entity.Option;
import gift.domain.entity.Product;
import gift.domain.exception.badRequest.OptionQuantityOutOfRangeException;
import gift.domain.exception.badRequest.OptionUpdateActionInvalidException;
import gift.domain.exception.conflict.OptionAlreadyExistsInProductException;
import gift.domain.exception.notFound.OptionNotFoundException;
import gift.domain.repository.OptionRepository;
import gift.global.WebConfig.Constants.Domain;
import gift.global.WebConfig.Constants.Domain.Option.QuantityUpdateAction;
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
    private void validateOptionIsUniqueInProduct(Product product, String optionName) {
        Objects.requireNonNullElse(product.getOptions(), new ArrayList<Option>()).stream()
            .filter(o -> o.getName().equals(optionName))
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
    public Option addOption(Product product, OptionAddRequest request) {
        validateOptionIsUniqueInProduct(product, request.name());
        Option option = optionRepository.save(request.toEntity(product));
        product.addOption(option);
        return option;
    }

    @Transactional
    public List<Option> addOptions(Product product, List<OptionAddRequest> request) {
        List<String> optionNames = new ArrayList<>(product.getOptions().stream()
            .map(Option::getName)
            .toList());
        optionNames.addAll(request.stream()
            .map(OptionAddRequest::name)
            .toList());
        List<String> distinctOptionNames = optionNames.stream().distinct().toList();

        //OptionRequest 리스트에 이름이 같은 서로 다른 옵션이 있거나 기존 옵션들과 겹치는 경우 이름 중복으로 예외
        if (distinctOptionNames.size() < product.getOptions().size() + request.size()) {
            throw new OptionAlreadyExistsInProductException();
        }

        request.forEach(req -> addOption(product, req));

        return optionRepository.findAllByProduct(product);
    }

    @Transactional
    public Option updateOptionById(Product product, Long optionId, OptionUpdateRequest request) {
        validateOptionIsInProduct(product, optionId);
        Option option = getOptionById(optionId);

        if (!option.getName().equals(request.name())) {
            validateOptionIsUniqueInProduct(product, request.name());
        }

        if (request.action().equals(QuantityUpdateAction.ADD)) {
            int updatedQuantity = option.getQuantity() + request.quantity();
            if (updatedQuantity > Domain.Option.QUANTITY_RANGE_MAX) {
                throw new OptionQuantityOutOfRangeException();
            }
            option.setName(request.name());
            option.add(request.quantity());
            return option;
        }

        if (request.action().equals(QuantityUpdateAction.SUBTRACT)) {
            int updatedQuantity = option.getQuantity() - request.quantity();
            if (updatedQuantity < Domain.Option.QUANTITY_RANGE_MIN) {
                throw new OptionQuantityOutOfRangeException();
            }
            option.setName(request.name());
            option.subtract(request.quantity());
            return option;
        }

        throw new OptionUpdateActionInvalidException();
    }

    @Transactional
    public void deleteOptionById(Product product, Long optionId) {
        validateOptionIsInProduct(product, optionId);
        Option option = getOptionById(optionId);
        optionRepository.delete(option);
    }
}
