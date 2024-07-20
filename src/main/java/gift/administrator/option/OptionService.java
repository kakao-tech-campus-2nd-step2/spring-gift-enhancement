package gift.administrator.option;

import gift.administrator.product.Product;
import java.util.List;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public class OptionService {

    private final OptionRepository optionRepository;

    public OptionService(OptionRepository optionRepository) {
        this.optionRepository = optionRepository;
    }

    public List<OptionDTO> getAllOptionsByProductId(long productId) {
        return optionRepository.findAllByProductId(productId).stream().map(OptionDTO::fromOption)
            .toList();
    }

    public List<OptionDTO> getAllOptions(){
        return optionRepository.findAll().stream().map(OptionDTO::fromOption).toList();
    }

    public List<OptionDTO> getAllOptionsByOptionId(List<Long> list){
        return optionRepository.findAllById(list).stream().map(OptionDTO::fromOption).toList();
    }

    public boolean existsByOptionIdAndProductId(long optionId, long productId){
        return optionRepository.existsByIdAndProductId(optionId, productId);
    }

    public OptionDTO findOptionById(long optionId) throws NotFoundException {
        return OptionDTO.fromOption(optionRepository.findById(optionId).orElseThrow(NotFoundException::new));
    }

    public OptionDTO addOption(OptionDTO optionDTO, Product product){
        Option option = optionDTO.toOption(product);
        option.setProduct(product);
        Option savedOption = optionRepository.save(option);
        return OptionDTO.fromOption(savedOption);
    }

    public OptionDTO subtractOptionQuantity(long optionId, int quantity) throws NotFoundException {
        Option option = optionRepository.findById(optionId).orElseThrow(NotFoundException::new);
        OptionDTO optionDTO = OptionDTO.fromOption(option);
        if(optionDTO.getQuantity()<quantity){
            throw new IllegalArgumentException("옵션의 수량이 부족합니다.");
        }
        optionDTO.subtract(quantity);
        return optionDTO;
    }

    public void deleteOptionByProductId(long productId){
        optionRepository.deleteByProductId(productId);
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
