package gift.service;


import gift.dto.OptionRequest;
import gift.exception.ForbiddenWordException;
import gift.exception.NameExceptionHandler;
import gift.exception.ResourceNotFoundException;
import gift.model.Option;
import gift.model.Options;
import gift.model.Product;
import gift.repository.OptionRepository;
import gift.repository.ProductRepository;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class OptionService {

    private final OptionRepository optionRepository;
    private final ProductRepository productRepository;

    public OptionService(OptionRepository optionRepository, ProductRepository productRepository) {
        this.optionRepository = optionRepository;
        this.productRepository = productRepository;
    }

    // 옵션 생성
    public Option createOption(final OptionRequest optionRequest) {
        var product = productRepository.findByName(optionRequest.getName())
            .orElseThrow(() -> new NoSuchElementException("상품이 존재하지 않습니다"));
        var option = new Option(optionRequest.getName(), optionRequest.getQuantity(), product);
        var foundOption = optionRepository.findAllByProduct(product);
        var options = new Options(foundOption);
        options.validate();
        return new Option(optionRequest.getName(), optionRequest.getQuantity(), product);
    }

    // 옵션 탐색
    public List<Option> retreiveOptions() {
        return optionRepository.findAll();
    }

    // 옵션 제거
    public void deleteOptions(Long id) {
        if (!optionRepository.existsById(id)) {
            throw new ResourceNotFoundException("없는 옵션입니다.");
        }
        optionRepository.deleteById(id);
    }

    // 옵션 ID 탐색
    public Option retrieveOption(Long id) {
        return optionRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("없는 옵션입니다."));
    }

    // 옵션 수정
    public Option updateOption(final OptionRequest optionRequest) {
        var option = optionRepository.findByName(optionRequest.getName())
            .orElseThrow(() -> new ResourceNotFoundException("존재하지 않는 옵션입니다"));
        var product = productRepository.findByName(optionRequest.getName())
            .orElseThrow(() -> new ResourceNotFoundException("존재하지 않는 상품입니다."));
        return optionRepository.save(new Option(option.getId(), optionRequest.getQuantity(), product));
    }
}
