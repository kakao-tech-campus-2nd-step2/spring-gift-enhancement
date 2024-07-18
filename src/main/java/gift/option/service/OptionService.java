package gift.option.service;

import gift.option.domain.Option;
import gift.option.domain.OptionDTO;
import gift.option.repository.OptionRepository;
import gift.product.domain.Product;
import gift.product.repository.ProductRepository;
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

    public Option save(OptionDTO optionDTO){
        return optionRepository.save(convertToEntity(optionDTO));
    }

    private OptionDTO convertToDTO(Option option){
        return new OptionDTO(
            option.getName(),
            option.getQuantity(),
            option.getProduct().getId()
        );
    }

    private Option convertToEntity(OptionDTO optionDTO){
        Option option = new Option(optionDTO.getName(), optionDTO.getQuantity());

        option.setProduct(productRepository.findById(optionDTO.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("productId" + optionDTO.getProductId() + "가 없다.")));
        return option;
    }
}
