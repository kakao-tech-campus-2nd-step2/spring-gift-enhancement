package gift.administrator.option;

import gift.administrator.product.Product;
import gift.administrator.product.ProductDTO;
import gift.administrator.product.ProductService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public class OptionService {

    private final OptionRepository optionRepository;
    private final ProductService productService;

    public OptionService(OptionRepository optionRepository, ProductService productService) {
        this.optionRepository = optionRepository;
        this.productService = productService;
    }

    public List<OptionDTO> getAllOptionsByProductId(long productId) {
        return optionRepository.findAllByProductId(productId).stream().map(OptionDTO::fromOption)
            .toList();
    }

    public List<OptionDTO> getAllOptions(){
        return optionRepository.findAll().stream().map(OptionDTO::fromOption).toList();
    }

    public OptionDTO findOptionById(long optionId) throws NotFoundException {
        return OptionDTO.fromOption(optionRepository.findById(optionId).orElseThrow(NotFoundException::new));
    }

    public OptionDTO addOptionByProductId(long productId, OptionDTO optionDTO)
        throws NotFoundException {
        ProductDTO productDTO = productService.getProductById(productId);
        Product product = productDTO.toProduct(productDTO,
            productService.getCategoryById(productDTO.getCategoryId()));
        Option option = optionDTO.toOption(optionDTO, product);
        product.addOption(option);
        return OptionDTO.fromOption(optionRepository.save(option));
    }

    public List<OptionDTO> addOptionsByProductId(long productId, List<OptionDTO> options)
        throws NotFoundException {
        for(OptionDTO optionDTO : options){
            if(optionRepository.existsByNameAndProductId(optionDTO.getName(), productId)){
                throw new IllegalArgumentException("옵션 이름은 같은 상품 내에서 중복될 수 없습니다.");
            }
        }
        List<OptionDTO> optionDTOList = new ArrayList<>();
        for (OptionDTO optionDTO : options) {
            optionDTOList.add(addOptionByProductId(productId, optionDTO));
        }
        return optionDTOList;
    }

    public OptionDTO updateOptionByOptionId(long optionId, OptionDTO optionDTO)
        throws NotFoundException {
        Option option = optionRepository.findById(optionId).orElseThrow(NotFoundException::new);
        if(optionRepository.existsByNameAndProductIdAndIdNot(option.getName(),
            optionDTO.getProductId(), optionId)){
            throw new IllegalArgumentException("옵션 이름은 같은 상품 내에서 중복될 수 없습니다.");
        }
        option.getProduct().removeOption(option);
        ProductDTO productDTO = productService.getProductById(optionDTO.getProductId());
        Product product = productDTO.toProduct(productDTO,
            productService.getCategoryById(productDTO.getCategoryId()));
        option.update(option.getName(), option.getQuantity(), product);
        option.getProduct().addOption(option);
        return OptionDTO.fromOption(optionRepository.save(option));
    }

    public void deleteOptionByOptionId(long optionId) throws NotFoundException {
        if(!optionRepository.existsById(optionId)){
            throw new IllegalArgumentException("없는 아이디입니다.");
        }
        Option option = optionRepository.findById(optionId).orElseThrow(NotFoundException::new);
        option.getProduct().removeOption(option);
        optionRepository.deleteById(optionId);
    }
}
