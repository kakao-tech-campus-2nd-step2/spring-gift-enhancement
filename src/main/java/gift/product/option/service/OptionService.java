package gift.product.option.service;

import gift.exception.CustomException;
import gift.exception.ErrorCode;
import gift.product.entity.Product;
import gift.product.option.dto.request.CreateOptionRequest;
import gift.product.option.entity.Option;
import gift.product.option.entity.Options;
import gift.product.option.repository.OptionRepository;
import gift.product.repository.ProductRepository;
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

    @Transactional
    public void createOption(CreateOptionRequest request) {
        Product product = productRepository.findById(request.productId())
            .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));
        Option option = new Option(request.name(), request.quantity(), product);

        Options options = new Options(optionRepository.findAllByProduct(product));
        options.validate(option);

        optionRepository.save(option);
    }

}
