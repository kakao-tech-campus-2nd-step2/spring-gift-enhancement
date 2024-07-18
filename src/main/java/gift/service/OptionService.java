package gift.service;

import gift.domain.Option;
import gift.entity.OptionEntity;
import gift.entity.ProductEntity;
import gift.error.AlreadyExistsException;
import gift.error.NotFoundException;
import gift.repository.OptionRepository;
import gift.repository.ProductRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OptionService {

    private final OptionRepository optionRepository;
    private final ProductRepository productRepository;

    public OptionService(OptionRepository optionRepository, ProductRepository productRepository) {
        this.optionRepository = optionRepository;
        this.productRepository = productRepository;
    }

    //해당 상품의 옵션들 조회
    @Transactional(readOnly = true)
    public List<Option> getAllOptionByProductId(Long productId) {
        ProductEntity productEntity = productRepository.findById(productId)
            .orElseThrow(() -> new NotFoundException("Product not found"));
        return productEntity.getOptionEntities().stream()
            .map(OptionEntity::toDto).toList();
    }

    //해당 상품 내의 단일 옵션 조회
    @Transactional(readOnly = true)
    public Option getOptionByProductIdAndId(Long productId, Long id) {
        ProductEntity productEntity = productRepository.findById(productId)
            .orElseThrow(() -> new NotFoundException("Product not found"));
        return productEntity.getOptionEntities().stream()
            .filter(o -> o.getId().equals(id))
            .findAny()
            .map(OptionEntity::toDto)
            .orElseThrow(() -> new NotFoundException("Option not found"));

    }

    //해당 상품 내의 옵션을 이름으로 조회
    @Transactional(readOnly = true)
    public Option getOptionByProductIdAndName(Long productId, String name) {
        ProductEntity productEntity = productRepository.findById(productId)
            .orElseThrow(() -> new NotFoundException("Product not found"));
        return productEntity.getOptionEntities().stream()
            .filter(o -> o.getName().equals(name))
            .findAny()
            .map(OptionEntity::toDto)
            .orElseThrow(() -> new NotFoundException("Option not found"));
    }

    //카테고리 추가 기능
    @Transactional
    public void addOption(Long productId, Option option) {
        checkAlreadyExists(null, option);
        OptionEntity optionEntity = optionRepository.save(new OptionEntity(option.getName(), option.getQuantity()));
        ProductEntity productEntity = productRepository.findById(productId)
            .orElseThrow(() -> new NotFoundException("Product not found"));
        productEntity.getOptionEntities().add(optionEntity);
    }

    //카테고리 수정 기능
    @Transactional
    public void updateOption(Long id, Option option) {
        checkAlreadyExists(id, option);
        OptionEntity optionEntity = optionRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Option not found"));
        optionEntity.setName(option.getName());
        optionEntity.setQuantity(option.getQuantity());
        optionRepository.save(optionEntity);
    }

    //카테고리 삭제 기능
    @Transactional
    public void deleteOption(Long id) {
        optionRepository.deleteById(id);
    }

    private void checkAlreadyExists(Long id, Option option) {
        boolean exists = optionRepository.findAll().stream()
            .anyMatch(o -> o.getName().equals(option.getName()) && !o.getId().equals(id));
        if (exists) {
            throw new AlreadyExistsException("Already Exists Option");
        }
    }

}
