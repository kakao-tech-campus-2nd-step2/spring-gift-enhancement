package gift.option.service;

import gift.option.dto.OptionReqDto;
import gift.option.exception.OptionDuplicatedNameException;
import gift.option.exception.OptionNotEnoughStockException;
import gift.option.repository.OptionRepository;
import gift.product.entity.Product;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OptionService {

    private final OptionRepository optionRepository;

    public OptionService(OptionRepository optionRepository) {
        this.optionRepository = optionRepository;
    }

    @Transactional
    public void addOptions(Product product, List<OptionReqDto> optionReqDtos) {
        checkDuplicatedOptionName(optionReqDtos);

        addOptionsToProduct(product, optionReqDtos);
    }

    @Transactional
    public void updateOptions(Product product, List<OptionReqDto> optionReqDtos) {
        checkDuplicatedOptionName(optionReqDtos);

        product.clearOptions();

        addOptionsToProduct(product, optionReqDtos);
    }

    @Transactional
    public void subtractStock(Long optionId, Integer quantity) {
        optionRepository.findById(optionId)
            .ifPresent(option -> {
                if (option.getQuantity() < quantity) {
                    throw OptionNotEnoughStockException.EXCEPTION;
                }
                option.subtractQuantity(quantity);
            });
    }

    private void addOptionsToProduct(Product product, List<OptionReqDto> optionReqDtos) {
        optionReqDtos.forEach(optionReqDto -> product.addOption(optionReqDto.toEntity()));
    }

    private void checkDuplicatedOptionName(List<OptionReqDto> optionReqDtos) {
        Set<String> optionNames = optionReqDtos.stream()
            .map(OptionReqDto::name)
            .collect(Collectors.toSet());

        if (optionNames.size() != optionReqDtos.size()) {
            throw OptionDuplicatedNameException.EXCEPTION;
        }
    }
}
