package gift.service;

import gift.constants.ErrorMessage;
import gift.dto.OptionDto;
import gift.entity.Option;
import gift.entity.Product;
import gift.repository.OptionJpaDao;
import gift.repository.ProductJpaDao;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;

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
    public List<OptionDto> getProductOptionList(Long productId) {
        findProductByIdOrElseThrow(productId);
        return optionJpaDao.findAllByProduct_id(productId).stream().map(OptionDto::new)
            .toList();
    }

    /**
     * optionId에 해당하는 옵션을 반환
     *
     * @param optionId
     * @return OptionDto
     */
    public OptionDto findOptionById(Long optionId) {
        return new OptionDto(findOptionByIdOrElseThrow(optionId));
    }

    public void addOption(OptionDto optionDto) {
        Product product = findProductByIdOrElseThrow(optionDto.getProductId());
        optionJpaDao.save(new Option(optionDto, product));
    }

    @Transactional
    public void editOption(OptionDto optionDto) {
        findProductByIdOrElseThrow(optionDto.getProductId());
        findOptionByIdOrElseThrow(optionDto.getId())
            .updateOption(optionDto);
    }

    public void deleteOption(Long optionId) {
        findOptionByIdOrElseThrow(optionId);
        optionJpaDao.deleteById(optionId);
    }

    /**
     * productId에 해당하는 상품이 존재하면 반환하고 아니면 NoSuchElementException
     *
     * @param productId
     * @return Product
     */
    private Product findProductByIdOrElseThrow(Long productId) {
        return productJpaDao.findById(productId)
            .orElseThrow(() -> new NoSuchElementException(ErrorMessage.PRODUCT_NOT_EXISTS_MSG));
    }

    /**
     * optionId에 해당하는 상품이 존재하면 반환하고 아니면 NoSuchElementException
     *
     * @param optionId
     * @return
     */
    private Option findOptionByIdOrElseThrow(Long optionId) {
        return optionJpaDao.findById(optionId)
            .orElseThrow(() -> new NoSuchElementException(ErrorMessage.OPTION_NOT_EXISTS_MSG));
    }
}
