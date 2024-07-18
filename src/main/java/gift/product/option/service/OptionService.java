package gift.product.option.service;

import gift.exception.CustomException;
import gift.exception.ErrorCode;
import gift.product.entity.Product;
import gift.product.option.dto.request.CreateOptionRequest;
import gift.product.option.entity.Option;
import gift.product.option.repository.OptionRepository;
import gift.product.repository.ProductRepository;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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

        if (request.name().length() > 50) {
            throw new IllegalArgumentException();
        }

        if (request.quantity() < 1 || request.quantity() > 100_000_000) {
            throw new IllegalArgumentException();
        }

        List<Option> options = optionRepository.findAllByProduct(product);
        for (Option option : options) {
            if(option.getName().equals(request.name())) {
                throw new IllegalArgumentException();
            }
        }

        Pattern pattern = Pattern.compile("^[\\w\\s()\\[\\]+\\-&/_]*$");
        Matcher matcher = pattern.matcher(request.name());
        if (!matcher.find()) {
            throw new IllegalArgumentException();
        }

        Option option = new Option(request.name(), request.quantity(), product);
        optionRepository.save(option);
    }

}
