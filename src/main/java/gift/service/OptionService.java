package gift.service;

import gift.entity.Option;
import gift.entity.Product;
import gift.exception.DataNotFoundException;
import gift.repository.OptionRepository;
import java.util.List;
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
            throw new IllegalArgumentException("asdfasdf");
        }
        optionRepository.save(option);
    }

    public void updateOption(Option option) {
        System.out.println(option.getId());
        System.out.println(option.getName());
        System.out.println(option.getQuantity());
        Option update = getOptionById(option.getId());
        update.setName(option.getName());
        update.setQuantity(option.getQuantity());
        optionRepository.save(update);
    }

    public void deleteOption(Option option) {
        optionRepository.delete(option);
    }

    public Product findProductById(Long id) {
        return productService.getProductById(id);
    }

    public Option getOptionById(Long id) {
        Optional<Option> option = optionRepository.findById(id);

        if (option.isEmpty()) {
            throw new DataNotFoundException("존재하지 않는 Option: Product를 찾을 수 없습니다.");
        }
        return option.get();
    }

    public List<Option> getOptionByProductId(Long productId) {
        return optionRepository.getOptionByProductId(productId);
    }

}
