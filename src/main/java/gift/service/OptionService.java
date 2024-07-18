package gift.service;

import gift.domain.Option;
import gift.entity.OptionEntity;
import gift.entity.ProductEntity;
import gift.error.AlreadyExistsException;
import gift.error.NotFoundException;
import gift.repository.OptionRepository;
import gift.repository.ProductRepository;
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
        ProductEntity productEntity = getProductEntity(productId);
        return optionRepository.findAllByProductEntity(productEntity).stream()
            .map(OptionEntity::toDto)
            .toList();
    }

    //해당 상품 내의 단일 옵션 조회
    @Transactional(readOnly = true)
    public Option getOptionByProductIdAndId(Long productId, Long id) {
        ProductEntity productEntity = getProductEntity(productId);
        OptionEntity optionEntity = optionRepository.findByProductEntityAndId(productEntity, id)
            .orElseThrow(() -> new NotFoundException("Option not found"));
        return OptionEntity.toDto(optionEntity);
    }

    //해당 상품 내의 옵션을 이름으로 조회
    @Transactional(readOnly = true)
    public Option getOptionByProductIdAndName(Long productId, String name) {
        ProductEntity productEntity = getProductEntity(productId);
        OptionEntity optionEntity = optionRepository.findByProductEntityAndName(productEntity, name)
            .orElseThrow(() -> new NotFoundException("Option not found"));
        return OptionEntity.toDto(optionEntity);
    }

    //카테고리 추가 기능
    @Transactional
    public void addCategory(Option option) {
        ProductEntity productEntity = productRepository.findById(option.getProductId())
            .orElseThrow(() -> new NotFoundException("Product not found"));
        checkAlreadyExists(option);
        OptionEntity optionEntity = new OptionEntity();
        optionEntity.setName(option.getName());
        optionEntity.setQuantity(option.getQuantity());
        optionEntity.setProductEntity(productEntity);
        optionRepository.save(optionEntity);
    }

    //카테고리 수정 기능
    @Transactional
    public void updateCategory(Long id, Option option) {
        ProductEntity productEntity = getProductEntity(option.getProductId());

        OptionEntity optionEntity = optionRepository.findByProductEntityAndId(productEntity, id)
            .orElseThrow(() -> new NotFoundException("Option not found"));

        checkAlreadyExists(option);

        optionEntity.setName(option.getName());
        optionEntity.setQuantity(option.getQuantity());
        optionEntity.setProductEntity(productEntity);
        optionRepository.save(optionEntity);
    }

    //카테고리 삭제 기능
    @Transactional
    public void deleteCategory(Long productId, Long id) {
        ProductEntity productEntity = getProductEntity(productId);
        OptionEntity optionEntity = optionRepository.findByProductEntityAndId(productEntity, id)
            .orElseThrow(() -> new NotFoundException("Option not found"));
        optionRepository.delete(optionEntity);
    }

    private ProductEntity getProductEntity(Long productId) {
        ProductEntity productEntity = productRepository.findById(productId)
            .orElseThrow(() -> new NotFoundException("Product not found"));
        return productEntity;
    }

    private void checkAlreadyExists(Option option) {
        boolean exists = optionRepository.findAll().stream()
            .anyMatch(o -> o.getName().equals(option.getName()) &&
                o.getQuantity().equals(option.getQuantity()) &&
                o.getProductEntity().getId().equals(option.getProductId()));
        if (exists) {
            throw new AlreadyExistsException("해당 옵션이 이미 존재 합니다!");
        }
    }

}
