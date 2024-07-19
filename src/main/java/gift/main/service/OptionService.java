package gift.main.service;

import gift.main.Exception.CustomException;
import gift.main.Exception.ErrorCode;
import gift.main.dto.OptionListRequest;
import gift.main.dto.OptionRequest;
import gift.main.dto.OptionResponse;
import gift.main.entity.Option;
import gift.main.entity.Product;
import gift.main.repository.OptionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OptionService {

    private final OptionRepository optionRepository;

    public OptionService(OptionRepository optionRepository) {
        this.optionRepository = optionRepository;
    }

    public List<OptionResponse> findOptionAll(long productId) {
        List<OptionResponse> options = optionRepository.findAllByProductId(productId)
                .orElseThrow(() -> new CustomException(ErrorCode.FAILED_OPTION_LOADING))
                .stream().map(option -> new OptionResponse(option))
                .collect(Collectors.toList());

        return options;
    }

    @Transactional
    public void registerOptionsFirstTime(Product product, OptionListRequest optionListRequest) {
        optionListRequest.optionRequests().stream()
                .map(optionRequest -> new Option(optionRequest, product))
                .forEach(option -> optionRepository.save(option));
    }

    @Transactional
    public void deleteOption(long productId, long optionId) {
        List<Option> options = optionRepository.findAllByProductId(productId)
                .orElseThrow(() -> new CustomException(ErrorCode.FAILED_OPTION_LOADING));

        if (options.size() <= 1) {
            throw new CustomException(ErrorCode.CANNOT_DELETED_OPTION);
        }

        optionRepository.deleteById(optionId);
    }

    @Transactional
    public void addOption(long productId, OptionRequest optionRequest) {
        List<Option> options = optionRepository.findAllByProductId(productId)
                .orElseThrow(() -> new CustomException(ErrorCode.FAILED_OPTION_LOADING));

        options.stream()
                .forEach(option -> option.isDuplicate(optionRequest));

        Product product = options.get(0).getProduct();
        Option newOption = new Option(optionRequest, product);
        optionRepository.save(newOption);
    }

    @Transactional
    public void updateOption(long productId, long optionId, OptionRequest optionRequest) {
        Option targetOption = optionRepository.findById(optionId)
                .orElseThrow(() -> new CustomException(ErrorCode.FAILED_OPTION_LOADING));

        List<Option> options = optionRepository.findAllByProductId(productId)
                .orElseThrow(() -> new CustomException(ErrorCode.FAILED_OPTION_LOADING));

        options.stream()
                .forEach(option -> option.isDuplicate(optionId, optionRequest));

        targetOption.updateValue(optionRequest);

        optionRepository.save(targetOption);
    }
}
