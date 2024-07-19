package gift.service;

import gift.domain.ProductOption.optionDetail;
import gift.domain.ProductOrder.decreaseProductOption;
import gift.entity.ProductOptionEntity;
import gift.errorException.BaseHandler;
import gift.mapper.ProductOptionMapper;
import gift.repository.ProductOptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductOrderService {

    private final ProductOptionRepository productOptionRepository;
    private final ProductOptionMapper productOptionMapper;

    @Autowired
    public ProductOrderService(ProductOptionRepository productOptionRepository,
        ProductOptionMapper productOptionMapper) {
        this.productOptionRepository = productOptionRepository;
        this.productOptionMapper = productOptionMapper;
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public optionDetail decreaseProductOption(Long productId, Long optionId,
        decreaseProductOption decrease) {
        ProductOptionEntity entity = productOptionRepository.findByProductIdAndId(
                productId, optionId)
            .orElseThrow(() -> new BaseHandler(HttpStatus.NOT_FOUND, "해당 상품의 옵션이 존재하지 않습니다."));

        if (entity.getQuantity() < decrease.getQuantity()) {
            throw new BaseHandler(HttpStatus.BAD_REQUEST, "재고가 부족합니다.");
        }

        entity.setQuantity(entity.getQuantity() - decrease.getQuantity());

        return productOptionMapper.toDetail(entity);
    }
}
