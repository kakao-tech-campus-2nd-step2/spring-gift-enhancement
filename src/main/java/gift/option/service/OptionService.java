package gift.option.service;

import gift.option.domain.Option;
import gift.option.dto.OptionServiceDto;
import gift.option.exception.DuplicateEmailException;
import gift.option.exception.DuplicateNicknameException;
import gift.option.exception.OptionNotFoundException;
import gift.option.repository.OptionRepository;
import gift.product.domain.Product;
import gift.product.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OptionService {
    private final OptionRepository optionRepository;
    private final ProductService productService;

    public OptionService(OptionRepository optionRepository, ProductService productService) {
        this.optionRepository = optionRepository;
        this.productService = productService;
    }

    public List<Option> getAllOptions() {
        return optionRepository.findAll();
    }

    public Option getOptionById(Long id) {
        return optionRepository.findById(id)
                .orElseThrow(OptionNotFoundException::new);
    }

    public Option createOption(OptionServiceDto optionServiceDto) {
        validateEmailAndNicknameUnique(optionServiceDto);
        Product product = productService.getProductById(optionServiceDto.productId());
        return optionRepository.save(optionServiceDto.toOption(product));
    }

    public Option updateOption(OptionServiceDto optionServiceDto) {
        validateOptionExists(optionServiceDto.id());
        validateEmailAndNicknameUnique(optionServiceDto);
        Product product = productService.getProductById(optionServiceDto.productId());
        return optionRepository.save(optionServiceDto.toOption(product));
    }

    public void deleteOption(Long id) {
        validateOptionExists(id);
        optionRepository.deleteById(id);
    }

    private void validateOptionExists(Long id) {
        if (!optionRepository.existsById(id)) {
            throw new OptionNotFoundException();
        }
    }

    private void validateEmailAndNicknameUnique(OptionServiceDto optionServiceDto) {

    }
}
