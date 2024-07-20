package gift.service;

import gift.constants.ErrorMessage;
import gift.dto.OptionEditRequest;
import gift.dto.OptionResponse;
import gift.dto.OptionSaveRequest;
import gift.entity.Option;
import gift.entity.Product;
import gift.exception.ProductOptionRequiredException;
import gift.repository.OptionJpaDao;
import gift.repository.ProductJpaDao;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OptionService {

    private final ProductJpaDao productJpaDao;
    private final OptionJpaDao optionJpaDao;

    public OptionService(ProductJpaDao productJpaDao, OptionJpaDao optionJpaDao) {
        this.productJpaDao = productJpaDao;
        this.optionJpaDao = optionJpaDao;
    }

    /**
     * productId에 해당하는 상품의 옵션들을 리스트로 반환.
     *
     * @param productId
     * @return List
     */
    public List<OptionResponse> getProductOptionList(Long productId) {
        findProductByIdOrElseThrow(productId);
        return findAllOptionsByProductId(productId);
    }

    /**
     * optionId에 해당하는 옵션을 반환
     *
     * @param optionId
     * @return OptionDto
     */
    public OptionResponse findOptionById(Long optionId) {
        return new OptionResponse(findOptionByIdOrElseThrow(optionId));
    }

    public void saveOption(OptionSaveRequest saveRequest) {
        Product product = findProductByIdOrElseThrow(saveRequest.getProductId());
        Option option = saveRequest.toEntity(product);

        if (product.isOptionDuplicate(option)) {
            throw new IllegalArgumentException(ErrorMessage.OPTION_NAME_ALREADY_EXISTS_MSG);
        }

        optionJpaDao.save(saveRequest.toEntity(product));
    }

    @Transactional
    public void editOption(OptionEditRequest editRequest) {
        findProductByIdOrElseThrow(editRequest.getProductId());
        findOptionByIdOrElseThrow(editRequest.getId())
            .updateOption(editRequest);
    }

    /**
     * 상품의 옵션을 삭제하는 메서드. 단, 옵션이 1개일 때는 삭제하지 않는다.
     */
    public void deleteOption(Long productId, Long optionId) {
        findOptionByIdOrElseThrow(optionId);
        if (findAllOptionsByProductId(productId).size() == 1) {
            throw new ProductOptionRequiredException(ErrorMessage.OPTION_MUST_MORE_THAN_ZERO);
        }
        optionJpaDao.deleteById(optionId);
    }

    /**
     * productId에 해당하는 상품이 존재하면 반환하고 아니면 NoSuchElementException
     */
    private Product findProductByIdOrElseThrow(Long productId) {
        return productJpaDao.findById(productId)
            .orElseThrow(() -> new NoSuchElementException(ErrorMessage.PRODUCT_NOT_EXISTS_MSG));
    }

    /**
     * optionId에 해당하는 상품이 존재하면 반환하고 아니면 NoSuchElementException
     */
    private Option findOptionByIdOrElseThrow(Long optionId) {
        return optionJpaDao.findById(optionId)
            .orElseThrow(() -> new NoSuchElementException(ErrorMessage.OPTION_NOT_EXISTS_MSG));
    }

    /**
     * 해당 상품의 옵션들을 리스트로 반환
     */
    private List<OptionResponse> findAllOptionsByProductId(Long productId) {
        return optionJpaDao.findAllByProduct_Id(productId).stream().map(OptionResponse::new)
            .toList();
    }
}
