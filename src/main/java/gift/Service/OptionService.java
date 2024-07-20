package gift.Service;

import gift.Exception.Option.OptionDuplicatedException;
import gift.Exception.Option.OptionNotFoundException;
import gift.Exception.ProductNotFoundException;
import gift.Model.DTO.OptionDTO;
import gift.Model.Entity.OptionEntity;
import gift.Model.Entity.ProductEntity;
import gift.Repository.OptionRepository;
import gift.Repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OptionService {
    private final OptionRepository optionRepository;
    private final ProductRepository productRepository;

    public OptionService(OptionRepository optionRepository, ProductRepository productRepository){
        this.optionRepository = optionRepository;
        this.productRepository = productRepository;
    }

    public void create(Long productId, OptionDTO optionDTO){
        Optional<OptionEntity> optionEntityOptional = optionRepository.findByName(optionDTO.name());
        Optional<ProductEntity> productEntityOptional = productRepository.findById(productId);

        if(optionEntityOptional.isPresent()){
            throw new OptionDuplicatedException();
        }

        if(productEntityOptional.isEmpty()){
            throw new ProductNotFoundException();
        }

        optionRepository.save(new OptionEntity(productEntityOptional.get(), optionDTO.name(), optionDTO.quantity()));
    }

    public List<OptionDTO> read(Long productId){
        Optional<ProductEntity> productEntityOptional = productRepository.findById(productId);

        if(productEntityOptional.isEmpty()){
            throw new ProductNotFoundException();
        }

        List<OptionEntity> optionEntityList = optionRepository.findByProductId(productId);
        List<OptionDTO> optionDTOList = new ArrayList<>();

        for(OptionEntity o: optionEntityList){
            optionDTOList.add(new OptionDTO(o.getId(), o.getName(), o.getQuantity()));
        }

        return optionDTOList;
    }

    public void update(Long productId, OptionDTO optionDTO){
        Optional<OptionEntity> optionEntityOptional = optionRepository.findByName(optionDTO.name());
        Optional<ProductEntity> productEntityOptional = productRepository.findById(productId);

        if(optionEntityOptional.isEmpty()){
            throw new OptionNotFoundException();
        }

        if(productEntityOptional.isEmpty()){
            throw new ProductNotFoundException();
        }

        OptionEntity optionEntity = optionEntityOptional.get();
        optionEntity.setName(optionDTO.name());
        optionEntity.setQuantity(optionDTO.quantity());

        optionRepository.save(optionEntity);
    }

    public void delete(Long id){
        Optional<OptionEntity> optionEntityOptional = optionRepository.findById(id);

        if(optionEntityOptional.isEmpty()){
            throw new OptionNotFoundException();
        }

        optionRepository.deleteById(id);
    }

    public void subtract(OptionDTO optionDTO, Long count){
        Optional<OptionEntity> optionEntityOptional = optionRepository.findByName(optionDTO.name());

        if(optionEntityOptional.isEmpty()){
            throw new OptionNotFoundException();
        }

        OptionEntity optionEntity = optionEntityOptional.get();
        long quantity = optionEntity.getQuantity() - count;

        if(quantity < 0) {
            throw new IllegalArgumentException();
        }

        optionEntity.setQuantity(quantity);
        optionRepository.save(optionEntity);
    }
}