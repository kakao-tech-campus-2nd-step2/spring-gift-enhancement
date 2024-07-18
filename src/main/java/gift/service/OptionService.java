package gift.service;

import gift.domain.Option;
import gift.domain.Option.Options;
import gift.domain.Product;
import gift.dto.requestDTO.OptionCreateRequestDTO;
import gift.dto.requestDTO.OptionNameUpdateRequestDTO;
import gift.dto.responseDTO.OptionResponseDTO;
import gift.repository.JpaOptionRepository;
import gift.repository.JpaProductRepository;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OptionService {
    private final JpaOptionRepository jpaOptionRepository;
    private final JpaProductRepository jpaProductRepository;

    public OptionService(JpaOptionRepository jpaOptionRepository,
        JpaProductRepository jpaProductRepository) {
        this.jpaOptionRepository = jpaOptionRepository;
        this.jpaProductRepository = jpaProductRepository;
    }

    @Transactional(readOnly = true)
    public List<OptionResponseDTO> getAllCategoriesByProductId(Long productId) {
        Product product = getProduct(productId);
        List<Option> optionList = jpaOptionRepository.findAllByProduct(product);

        return optionList
            .stream()
            .map(OptionResponseDTO::of)
            .toList();
    }

    public Long addOption(Long productId, OptionCreateRequestDTO optionCreateRequestDTO) {
        Product product = getProduct(productId);
        Options optionList = new Options(jpaOptionRepository.findAllByProduct(product));
        optionList.validDuplicateName(optionCreateRequestDTO.name());
        Option option = optionCreateRequestDTO.toEntity(product);
        return jpaOptionRepository.save(option).getId();
    }

    public Long updateOptionName(Long optionId,
        OptionNameUpdateRequestDTO optionNameUpdateRequestDTO) {
        Option option = getOption(optionId);
        option.updateName(optionNameUpdateRequestDTO.name());
        return option.getId();
    }

    public Long deleteOption(Long optionId) {
        Option option = getOption(optionId);
        jpaOptionRepository.delete(option);
        return option.getId();
    }

    private Option getOption(Long optionId) {
        return jpaOptionRepository.findById(optionId)
            .orElseThrow(() -> new NoSuchElementException("id가 잘못되었습니다."));
    }

    private Product getProduct(Long productId) {
        return jpaProductRepository.findById(productId)
            .orElseThrow(() -> new NoSuchElementException("id가 잘못되었습니다."));
    }
}