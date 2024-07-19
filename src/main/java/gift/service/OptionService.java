package gift.service;


import gift.dto.OptionRequestDTO;
import gift.entity.Option;
import gift.entity.Product;
import gift.exception.OptionException;
import gift.repository.OptionRepository;
import gift.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public List<Option> findByProductId(Long productId) {
        List<Option> options = optionRepository.findByProductId(productId);
        return options;
    }

    public void save(Long productId, OptionRequestDTO optionRequestDTO) {

        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new OptionException("상품이 존재하지 않습니다"));

        String optionName = optionRequestDTO.name();
        Optional<Option> existingOption = optionRepository.findByProductIdAndName(productId, optionName);
        if(existingOption.isPresent()) {
            throw new OptionException("옵션 이름이 존재합니다.");
        }

        optionRepository.save(toEntity(existingProduct, optionRequestDTO));
    }

    private Option toEntity(Product product, OptionRequestDTO optionRequestDTO ) {
        String optionName = optionRequestDTO.name();
        int quantity = optionRequestDTO.quantity();
        Option option = new Option(optionName, quantity, product);
        return option;
    }

}
