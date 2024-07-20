package gift.mapper;

import gift.DTO.ProductOptionDTO;
import gift.entity.ProductEntity;
import gift.entity.ProductOptionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductOptionMapper {

    @Autowired
    CategoryMapper categoryMapper;


    /**
     * ProductOptionEntity를 ProductOptionDTO로 변환하는 메서드
     *
     * @param productOptionEntity 변환할 ProductOptionEntity 객체
     * @return 변환된 ProductOptionDTO 객체
     */
    public ProductOptionDTO toProductOptionDTO(ProductOptionEntity productOptionEntity) {
        return new ProductOptionDTO(
                productOptionEntity.getId(),
                productOptionEntity.getName(),
                productOptionEntity.getQuantity()
        );
    }

    /**
     * ProductOptionDTO를 ProductOptionEntity로 변환하는 메서드
     *
     * @param productOptionDTO 변환할 ProductOptionDTO 객체
     * @param productEntity 상품 엔티티
     * @param idRequired ID 필요 여부
     * @return 변환된 ProductOptionEntity 객체
     */
    public ProductOptionEntity toProductOptionEntity(ProductOptionDTO productOptionDTO, ProductEntity productEntity, boolean idRequired) {
        Long id = null;
        if (idRequired) {
            id = productOptionDTO.id();
        }
        return new ProductOptionEntity(
                id,
                productOptionDTO.name(),
                productOptionDTO.quantity(),
                productEntity
            );
    }

}
