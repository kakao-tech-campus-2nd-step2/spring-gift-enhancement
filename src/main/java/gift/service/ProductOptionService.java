package gift.service;

import gift.domain.ProductOption.CreateOption;
import gift.domain.ProductOption.UpdateOption;
import gift.domain.ProductOption.getList;
import gift.domain.ProductOption.optionDetail;
import gift.domain.ProductOption.optionSimple;
import gift.entity.ProductEntity;
import gift.entity.ProductOptionEntity;
import gift.errorException.BaseHandler;
import gift.mapper.ProductOptionMapper;
import gift.repository.ProductOptionRepository;
import gift.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductOptionService {

    private final ProductOptionRepository productOptionRepository;
    private final ProductRepository productRepository;
    private final ProductOptionMapper productOptionMapper;

    @Autowired
    public ProductOptionService(ProductOptionRepository productOptionRepository,
        ProductRepository productRepository, ProductOptionMapper productOptionMapper) {
        this.productOptionRepository = productOptionRepository;
        this.productRepository = productRepository;
        this.productOptionMapper = productOptionMapper;
    }

    public Page<optionSimple> getProductOptionList(long productId, getList req) {
        return productOptionMapper.toSimple(
            productOptionRepository.findAllByProductId(productId, req.toPageable()));
    }

    public optionDetail getProductOption(long productId, long id) {
        return productOptionMapper.toDetail(
            productOptionRepository.findByProductIdAndId(productId, id)
                .orElseThrow(() -> new BaseHandler(HttpStatus.NOT_FOUND, "해당 상품의 옵션이 존재하지 않습니다.")));

    }

    public Long createProductOption(long productId, CreateOption create) {
        if (productOptionRepository.findByNameAndProductId(create.getName(), productId)
            .isPresent()) {
            throw new BaseHandler(HttpStatus.UNAUTHORIZED, "옵션의 이름이 중복됩니다.");
        }

        ProductEntity productEntity = productRepository.findById(productId)
            .orElseThrow(() -> new BaseHandler(HttpStatus.NOT_FOUND, "상품이 존재하지 않습니다."));

        ProductOptionEntity entity = productOptionMapper.toEntity(create, productEntity);
        productOptionRepository.save(entity);
        return entity.getId();
    }

    @Transactional
    public Long updateProductOption(long productId, long id, UpdateOption update) {
        ProductOptionEntity entity = productOptionRepository.findByProductIdAndId(
                productId, id)
            .orElseThrow(() -> new BaseHandler(HttpStatus.NOT_FOUND, "해당 상품의 옵션이 존재하지 않습니다."));

        if (entity.getName() != update.getName() &&
            productOptionRepository.findByNameAndProductId(update.getName(), productId)
                .isPresent()) {
            throw new BaseHandler(HttpStatus.UNAUTHORIZED, "옵션의 이름이 중복됩니다.");
        }

        productOptionMapper.toUpdate(entity, update);
        return entity.getId();
    }

    public Long deleteProductOption(long productId, long id) {
        if (productOptionRepository.findAllByProductId(productId).size() == 1) {
            throw new BaseHandler(HttpStatus.UNAUTHORIZED, "상품은 최소 1개 이상의 옵션을 가져야 합니다.");
        }

        ProductOptionEntity entity = productOptionRepository.findByProductIdAndId(
                productId, id)
            .orElseThrow(() -> new BaseHandler(HttpStatus.NOT_FOUND, "해당 상품의 옵션이 존재하지 않습니다."));
        productOptionRepository.delete(entity);

        return entity.getId();
    }
}
