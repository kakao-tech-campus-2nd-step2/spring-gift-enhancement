package gift.option.service;

import gift.option.domain.Option;
import gift.option.domain.OptionDTO;
import gift.option.repository.OptionRepository;
import gift.product.domain.Product;
import gift.product.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class OptionService {

    private final OptionRepository optionRepository;
    private final ProductRepository productRepository;

    public OptionService(OptionRepository optionRepository, ProductRepository productRepository) {
        this.optionRepository = optionRepository;
        this.productRepository = productRepository;
    }

    public List<OptionDTO> findAll(){
        return optionRepository.findAll()
            .stream().map(this::convertToDTO)
            .toList();
    }

    public List<OptionDTO> findAllByProductId(Long productId){
        return optionRepository.findAllByProductId(productId)
            .stream().map(this::convertToDTO)
            .toList();
    }
    public List<Option> saveAll(List<OptionDTO> optionDTOList){
        return optionRepository.saveAll(optionDTOList.stream()
            .map(this::convertToEntity)
            .toList());
    }

    public List<Option> saveAllwithProductId(List<OptionDTO> optionDTOList, Long productId){
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new EntityNotFoundException("Product with id " + productId + " not found"));

        List<Option> options = optionDTOList.stream()
            .map(optionDTO -> {
                Option option = convertToEntity(optionDTO);
                option.setProduct(product); // Set the product for each option
                return option;
            })
            .toList();

        return optionRepository.saveAll(options);
    }

    public Option save(OptionDTO optionDTO){
        return optionRepository.save(convertToEntity(optionDTO));
    }

    public OptionDTO convertToDTO(Option option){
        return new OptionDTO(
            option.getName(),
            option.getQuantity(),
            option.getProduct().getId()
        );
    }

    public Option convertToEntity(OptionDTO optionDTO){
        Option option = new Option(optionDTO.getName(), optionDTO.getQuantity());
        option.setProduct(productRepository.findById(optionDTO.getProductId())
            .orElseThrow(() -> new IllegalArgumentException("productId 없음.")));
        return option;
    }
}
