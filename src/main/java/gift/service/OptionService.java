package gift.service;


import gift.dto.OptionRequestDTO;
import gift.dto.OptionResponseDTO;
import gift.entity.Option;
import gift.entity.Product;
import gift.exception.ProductException;
import gift.exception.optionException.OptionException;
import gift.repository.OptionRepository;
import gift.repository.ProductRepository;
import jakarta.transaction.Transactional;
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

    public List<OptionResponseDTO> findAll() {
        List<Option> options = optionRepository.findAll();
        return options.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public OptionResponseDTO getOption(Long optionId) {
        Option option = optionRepository.findById(optionId)
                .orElseThrow(()-> new OptionException("Option not found"));
        return toDto(option);
    }

    @Transactional
    public void addOption(OptionRequestDTO optionRequestDTO) {
        Long productId = optionRequestDTO.productId();
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ProductException("상품이 존재하지 않습니다"));

        String optionName = optionRequestDTO.name();
        if (optionRepository.findByProductIdAndName(productId, optionName).isPresent()) {
            throw new OptionException("중복되는 옵션 이름입니다.");
        }

        existingProduct.addOption(optionRequestDTO);
    }

    @Transactional
    public void removeOption(Long optionId) {
        Option option = optionRepository.findById(optionId)
                .orElseThrow(() -> new OptionException("product not found!"));
        optionRepository.delete(option);
    }

    @Transactional
    public void updateOption(Long optionId , OptionRequestDTO optionRequestDTO) {
        Option option = optionRepository.findById(optionId)
                .orElseThrow(() -> new OptionException("product not found!"));
        option.updateOption(optionRequestDTO);
    }

    @Transactional
    public void subtractOption(Long optionId, int quantity) {
        Option option = optionRepository.findById(optionId)
                .orElseThrow(() -> new OptionException("product not found!"));
        if (quantity >= option.getQuantity()) {
            removeOption(optionId);
            return ;
        }
        option.subtract(quantity);

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
