package gift.Service;

import gift.Entity.OptionEntity;
import gift.Entity.ProductEntity;
import gift.DTO.OptionDTO;
import gift.Repository.OptionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OptionService {

    @Autowired
    private OptionRepository optionRepository;

    public OptionDTO createOption(OptionDTO optionDTO) {
        validateOptionNameUniqueness(optionDTO.getName(), optionDTO.getProductId());
        OptionEntity optionEntity = new OptionEntity(
                optionDTO.getName(),
                optionDTO.getQuantity(),
                new ProductEntity()
        );
        optionEntity = optionRepository.save(optionEntity);
        return convertToDTO(optionEntity);
    }

    public OptionDTO getOptionById(Long id) {
        OptionEntity optionEntity = optionRepository.findById(id).orElseThrow(() -> new RuntimeException("Option을 찾을 수 없습니다."));
        return convertToDTO(optionEntity);
    }

    public List<OptionDTO> getAllOptions() {
        List<OptionEntity> optionEntities = optionRepository.findAll();
        return optionEntities.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public OptionDTO updateOption(Long id, OptionDTO optionDTO) {
        OptionEntity optionEntity = optionRepository.findById(id).orElseThrow(() -> new RuntimeException("Option을 찾을 수 없습니다."));
        if (!optionEntity.getName().equals(optionDTO.getName())) {
            validateOptionNameUniqueness(optionDTO.getName(), optionDTO.getProductId());
        }
        optionEntity.setName(optionDTO.getName());
        optionEntity.setQuantity(optionDTO.getQuantity());
        optionEntity = optionRepository.save(optionEntity);
        return convertToDTO(optionEntity);
    }


    @Transactional
    public OptionDTO substractQuantity(Long id,Long substractQuantity, OptionDTO optionDTO) {
        OptionEntity optionEntity = optionRepository.findById(id).orElseThrow(() -> new RuntimeException("Option을 찾을 수 없습니다."));
        if (!optionEntity.getName().equals(optionDTO.getName())) {
            validateOptionNameUniqueness(optionDTO.getName(), optionDTO.getProductId());
        }
        if(substractQuantity > optionEntity.getQuantity()) {
            throw new RuntimeException("감소시키려는 수량보다 남은 재고가 적습니다.");
        }
        optionEntity.setQuantity(optionEntity.getQuantity() - substractQuantity);
        optionEntity.setName(optionDTO.getName());
        optionEntity = optionRepository.save(optionEntity);
        return convertToDTO(optionEntity);
    }


    public void deleteOption(Long id) {
        optionRepository.deleteById(id);
    }

    private OptionDTO convertToDTO(OptionEntity optionEntity) {
        return new OptionDTO(
                optionEntity.getId(),
                optionEntity.getName(),
                optionEntity.getQuantity(),
                optionEntity.getProduct() != null ? optionEntity.getProduct().getId() : null
        );
    }

    private void validateOptionNameUniqueness(String name, Long productId) {
        List<OptionEntity> options = optionRepository.findByProductId(productId);
        for (OptionEntity option : options) {
            if (option.getName().equals(name)) {
                throw new RuntimeException("동일한 상품 내에서 옵션 이름이 중복될 수 없습니다.");
            }
        }
    }
}
