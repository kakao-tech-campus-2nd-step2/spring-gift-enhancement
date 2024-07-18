package gift.service;

import gift.entity.Option;
import gift.entity.Product;
import gift.repository.OptionRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class OptionService {

    private final OptionRepository optionRepository;
    private final ProductService productService;

    public OptionService(OptionRepository optionRepository, ProductService productService) {
        this.optionRepository = optionRepository;
        this.productService = productService;
    }

    private boolean isDuplicateName(Option option) {
        Optional<Option> options = optionRepository.findByIdAndName(
            option.getProduct().getId(), option.getName()
        );
        return options.isPresent();
    }

    public void addOption(Option option) {
        if (isDuplicateName(option)) {
            throw new IllegalArgumentException("Option 이름은 중복될 수 없습니다.");
        }
        optionRepository.save(option);
    }

    public void deleteOption(Option option) {
        optionRepository.delete(option);
    }

    public Product findProductById(Long id){
       return productService.getProductById(id);
    }

}
