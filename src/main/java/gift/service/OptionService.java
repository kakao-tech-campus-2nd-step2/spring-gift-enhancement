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
        return optionJpaDao.findAllByProduct_Id(productId).stream().map(OptionDto::new)
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
        assertOptionNotDuplicate(optionDto.getName(), optionDto.getProductId());
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
     * optionName과 productId로 같은 상품에 중복된 옵션이 존재한다면 IllegalArgumentException
     */
    private void assertOptionNotDuplicate(String optionName, Long productId) {
        optionJpaDao.findByNameAndProduct_Id(optionName, productId)
            .ifPresent(v -> {
                throw new IllegalArgumentException(ErrorMessage.OPTION_NAME_ALREADY_EXISTS_MSG);
            });
    }
}
