package gift.administrator.option;

import gift.administrator.product.Product;
import java.util.ArrayList;
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

    public OptionDTO findOptionById(long optionId) throws NotFoundException {
        return OptionDTO.fromOption(optionRepository.findById(optionId).orElseThrow(NotFoundException::new));
    }

    public OptionDTO addOptionByProductId(long productId, OptionDTO optionDTO, Product product){
        Option option = optionDTO.toOption(product);
        product.addOption(option);
        return OptionDTO.fromOption(optionRepository.save(option));
    }

    public List<OptionDTO> addOptionsByProductId(long productId, List<OptionDTO> options, Product product)
        throws NotFoundException {
        for(OptionDTO optionDTO : options){
            if(optionRepository.existsByNameAndProductId(optionDTO.getName(), productId)){
                throw new IllegalArgumentException("옵션 이름은 같은 상품 내에서 중복될 수 없습니다.");
            }
        }
        List<OptionDTO> optionDTOList = new ArrayList<>();
        for (OptionDTO optionDTO : options) {
            optionDTOList.add(addOptionByProductId(productId, optionDTO, product));
        }
        return optionDTOList;
    }

    public List<OptionDTO> addOptions(List<OptionDTO> options, Product product){
        for(OptionDTO optionDTO : options){
            if(optionRepository.existsByName(optionDTO.getName())){
                throw new IllegalArgumentException("옵션 이름은 같은 상품 내에서 중복될 수 없습니다.");
            }
        }
        List<OptionDTO> optionDTOList = new ArrayList<>();
        for (OptionDTO optionDTO : options) {
            optionDTOList.add(addOption(optionDTO, product));
        }
        return optionDTOList;
    }

    public OptionDTO addOption(OptionDTO optionDTO, Product product){
        Option option = optionDTO.toOption(product);
        return OptionDTO.fromOption(optionRepository.save(option));
    }

    public OptionDTO updateOptionByOptionId(long optionId, OptionDTO optionDTO, Product product)
        throws NotFoundException {
        Option option = optionRepository.findById(optionId).orElseThrow(NotFoundException::new);
        if(optionRepository.existsByNameAndProductIdAndIdNot(option.getName(),
            optionDTO.getProductId(), optionId)){
            throw new IllegalArgumentException("옵션 이름은 같은 상품 내에서 중복될 수 없습니다.");
        }
        option.getProduct().removeOption(option);
        option.update(option.getName(), option.getQuantity(), product);
        option.getProduct().addOption(option);
        return OptionDTO.fromOption(optionRepository.save(option));
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
