package gift.option.service;

import gift.option.dto.OptionReqDto;
import gift.option.exception.OptionDuplicatedNameException;
import gift.product.entity.Product;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OptionService {

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
