package gift.product.option.service;

import gift.exception.CustomException;
import gift.exception.ErrorCode;
import gift.product.entity.Product;
import gift.product.option.dto.request.CreateOptionRequest;
import gift.product.option.dto.request.UpdateOptionRequest;
import gift.product.option.dto.response.OptionResponse;
import gift.product.option.entity.Option;
import gift.product.option.entity.Options;
import gift.product.option.repository.OptionRepository;
import gift.product.repository.ProductRepository;
import gift.util.mapper.OptionMapper;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OptionService {

    private final ProductRepository productRepository;
    private final OptionRepository optionRepository;

    public OptionService(ProductRepository productRepository, OptionRepository optionRepository) {
        this.productRepository = productRepository;
        this.optionRepository = optionRepository;
    }

    @Transactional(readOnly = true)
    public List<OptionResponse> getProductOptions(Long productId) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));

        return product.getOptions()
            .stream()
            .map(OptionMapper::toResponse)
            .toList();
    }

    @Transactional
    public Long createOption(CreateOptionRequest request) {
        Product product = productRepository.findById(request.productId())
            .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));
        Option option = new Option(request.name(), request.quantity(), product);

        Options options = new Options(optionRepository.findAllByProduct(product));
        options.validate(option);

        product.addOption(option);
        Option saved = optionRepository.save(option);

        return saved.getId();
    }

    @Transactional
    public void updateOption(Long productId, UpdateOptionRequest request) {
        Option option = optionRepository.findById(request.id())
            .orElseThrow(() -> new CustomException(ErrorCode.OPTION_NOT_FOUND));
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));

        Options options = new Options(optionRepository.findAllByProduct(product));
        options.validate(request);

        option.edit(request);
    }

    public void deleteOption(Long productId, Long id) {
        Option option = optionRepository.findById(id)
            .orElseThrow(() -> new CustomException(ErrorCode.OPTION_NOT_FOUND));
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));

        product.removeOption(option);
        optionRepository.delete(option);
    }
}
