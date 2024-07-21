package gift.service;


import gift.dto.OptionRequestDTO;
import gift.dto.OptionResponseDTO;
import gift.entity.Option;
import gift.entity.Product;
import gift.exception.optionException.OptionException;
import gift.repository.OptionRepository;
import gift.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OptionService {

    private final OptionRepository optionRepository;
    private final ProductRepository productRepository;

    @Autowired
    public OptionService(OptionRepository optionRepository, ProductRepository productRepository) {
        this.optionRepository = optionRepository;
        this.productRepository = productRepository;
    }

    public List<Option> findAll() {
        return optionRepository.findAll();
    }

    public List<OptionResponseDTO> findByProductId(Long productId) {
        List<Option> options = optionRepository.findByProductId(productId);
        return options.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public void addOption(OptionRequestDTO optionRequestDTO) {
        Long productId = optionRequestDTO.productId();
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new OptionException("상품이 존재하지 않습니다"));
        String optionName = optionRequestDTO.name();
        Optional<Option> existingOption = optionRepository.findByProductIdAndName(productId, optionName);
        if(existingOption.isPresent()) {
            throw new OptionException("옵션 이름이 존재합니다.");
        }

        optionRepository.save(toEntity(existingProduct, optionRequestDTO));
    }

    public void updateOption(Long productId, Long optionId, OptionRequestDTO optionRequestDTO) {
        Optional<Option> option = optionRepository.findById(productId);


    }

    public void removeOption(Long productId, Long optionId) {

    }

    @Description("request DTO -> entity")
    private Option toEntity(Product product, OptionRequestDTO optionRequestDTO) {
        String optionName = optionRequestDTO.name();
        int quantity = optionRequestDTO.quantity();
        Option option = new Option(optionName, quantity, product);
        return option;
    }

    @Description("entity -> responseDTO")
    private OptionResponseDTO toDto(Option option) {
        Long productId = option.getProduct()
                .getId();
        String optionName = option.getName();
        int quantity = option.getQuantity();

        OptionResponseDTO optionResponseDTO = new OptionResponseDTO(productId, optionName, quantity);
        return optionResponseDTO;
    }

}
