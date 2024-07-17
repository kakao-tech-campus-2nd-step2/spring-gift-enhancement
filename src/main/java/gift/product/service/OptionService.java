package gift.product.service;

import static gift.product.exception.GlobalExceptionHandler.NOT_EXIST_ID;

import gift.product.dto.OptionDTO;
import gift.product.exception.InvalidIdException;
import gift.product.model.Option;
import gift.product.model.Product;
import gift.product.repository.OptionRepository;
import gift.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class OptionService {

    private final OptionRepository optionRepository;
    private final ProductRepository productRepository;

    @Autowired
    public OptionService(OptionRepository optionRepository, ProductRepository productRepository) {
        this.optionRepository = optionRepository;
        this.productRepository = productRepository;
    }

    public Page<Option> getAllOptions(Long id, Pageable pageable) {
        System.out.println("[OptionService] getAllOptions()");
        return optionRepository.findAllByProductId(id, pageable);
    }

    public void registerOption(OptionDTO optionDTO) {
        System.out.println("[OptionService] registerOption()");
        Product product = productRepository.findById(optionDTO.getProductId())
            .orElseThrow(() -> new InvalidIdException(NOT_EXIST_ID));
        optionRepository.save(
            new Option(
                optionDTO.getName(),
                optionDTO.getQuantity(),
                product
            )
        );
    }

    public void updateOption(Long id, OptionDTO optionDTO) {
        System.out.println("[OptionService] updateOption()");

        Product product = optionRepository.findById(id)
            .orElseThrow(() -> new InvalidIdException(NOT_EXIST_ID))
            .getProduct();

        optionRepository.save(
            new Option(
                id,
                optionDTO.getName(),
                optionDTO.getQuantity(),
                product
            )
        );
    }

    public void deleteOption(Long id) {
        System.out.println("[OptionService] deleteOption()");
        try {
            optionRepository.deleteById(id);
        }catch(Exception e) {
            System.out.println("[OptionService] deleteOption(): " + e.getMessage());
        }
    }
}
