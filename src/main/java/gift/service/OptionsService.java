package gift.service;

import gift.exception.option.DeleteOptionsException;
import gift.exception.option.DuplicateOptionsException;
import gift.exception.option.NotFoundOptionsException;
import gift.exception.product.NotFoundProductException;
import gift.model.Options;
import gift.repository.OptionsRepository;
import gift.repository.ProductRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class OptionsService {

    private final ProductRepository productRepository;
    private final OptionsRepository optionsRepository;

    public OptionsService(ProductRepository productRepository,
        OptionsRepository optionsRepository) {
        this.productRepository = productRepository;
        this.optionsRepository = optionsRepository;
    }

    public List<Options> getAllOptions(Long productId) {
        return optionsRepository.findAllByProductId(productId);
    }

    public Options getOption(Long id) {
        return optionsRepository.findById(id)
            .orElseThrow(NotFoundOptionsException::new);
    }

    @Transactional
    public Options addOption(String name, Integer quantity, Long productId) {
        return productRepository.findById(productId)
            .map(product -> {
                    if (optionsRepository.findByNameAndProductId(name, productId).isPresent()) {
                        throw new DuplicateOptionsException();
                    }
                    return optionsRepository.save(new Options(name, quantity, product));
                }
            ).orElseThrow(NotFoundProductException::new);
    }

    @Transactional
    public Options updateOption(Long id, String name, Integer quantity, Long productId) {
        productRepository.findById(productId)
            .orElseThrow(NotFoundProductException::new);

        return optionsRepository.findById(id)
            .map(options -> {
                options.updateOption(name, quantity);
                return options;
            }).orElseThrow(NotFoundOptionsException::new);
    }

    @Transactional
    public Options subtractQuantity(Long id, Integer subQuantity, Long productId) {
        productRepository.findById(productId)
            .orElseThrow(NotFoundProductException::new);

        return optionsRepository.findById(id)
            .map(options -> {
                options.subtractQuantity(subQuantity);
                return options;
            }).orElseThrow(NotFoundOptionsException::new);
    }

    @Transactional
    public Long deleteOption(Long id, Long productId) {
        if (optionsRepository.optionsCountByProductId(productId) < 2) {
            throw new DeleteOptionsException();
        }

        return optionsRepository.findById(id)
            .map(options -> {
                optionsRepository.delete(options);
                return options.getId();
            }).orElseThrow(NotFoundOptionsException::new);
    }

    @Transactional
    public void deleteAllOptions(Long productId) {
        optionsRepository.deleteAllByProductId(productId);
    }


}
