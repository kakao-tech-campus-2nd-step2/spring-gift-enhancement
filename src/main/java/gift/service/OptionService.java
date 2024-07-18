package gift.service;

import gift.dto.OptionDto;
import gift.model.Option;
import gift.model.Product;
import gift.repository.OptionRepository;
import gift.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class OptionService {

    private final OptionRepository optionRepository;
    private final ProductRepository productRepository;

    public OptionService(OptionRepository optionRepository, ProductRepository productRepository) {
        this.optionRepository = optionRepository;
        this.productRepository = productRepository;
    }

    public List<Option> getOptionsByProductId(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 상품입니다."));
        return product.getOptions();
    }

    public Option addOption(Long productId, OptionDto optionDto) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 상품입니다."));

        boolean isDuplicate = product.getOptions().stream()
                .anyMatch(option -> option.getName().equals(optionDto.getName()));
        if (isDuplicate) {
            throw new IllegalArgumentException("이미 존재하는 옵션 이름입니다.");
        }

        Option option = new Option(optionDto.getName(), optionDto.getQuantity());
        optionRepository.save(option);
        product.getOptions().add(option);
        productRepository.save(product);
        return option;
    }

    public Option updateOption(Long optionId, OptionDto optionDto) {
        optionRepository.findById(optionId)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 옵션입니다."));
        Product product = optionRepository.findProductByOptionId(optionId)
                .orElseThrow(() -> new NoSuchElementException("상품이 존재하지 않는 옵션입니다."));

        boolean isDuplicate = product.getOptions().stream()
                .anyMatch(opt -> opt.getName().equals(optionDto.getName()) && !opt.getId().equals(optionId));
        if (isDuplicate) {
            throw new IllegalArgumentException("이미 존재하는 옵션 이름입니다.");
        }

        Option updatedOption = new Option(optionDto.getName(), optionDto.getQuantity());
        return optionRepository.save(updatedOption);
    }

    @Transactional
    public void deleteOption(Long optionId) {
        Option option = optionRepository.findById(optionId)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 옵션입니다."));
        Product product = optionRepository.findProductByOptionId(optionId)
                .orElseThrow(() -> new NoSuchElementException("상품이 존재하지 않는 옵션입니다."));

        if (product.getOptions().size() <= 1) {
            throw new IllegalArgumentException("상품에는 최소 하나의 옵션이 있어야 합니다");
        }

        product.getOptions().remove(option);
        productRepository.save(product);
        optionRepository.deleteById(optionId);
    }

}
