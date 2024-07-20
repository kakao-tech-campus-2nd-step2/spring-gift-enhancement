package gift.service;

import gift.dto.OptionDto;
import gift.model.product.Option;
import gift.model.product.Product;
import gift.repository.OptionRepository;
import gift.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OptionService {
    private final OptionRepository optionRepository;
    private final ProductRepository productRepository;

    public OptionService(OptionRepository optionRepository,ProductRepository productRepository){
        this.optionRepository = optionRepository;
        this.productRepository = productRepository;
    }

    public List<Option> getAllOptionsById(Long productId){
        return optionRepository.findByProductId(productId);
    }

    public void addNewOption(Long productId, OptionDto optionDto){
        Product product = productRepository.findById(productId).get();
        Option option = new Option(product, optionDto.name(), optionDto.amount());
        optionRepository.save(option);
    }

    public void subtractAmount(Long optionId, int amount){
        Optional<Option> optionalOption = optionRepository.findById(optionId);
        if (!optionalOption.isPresent()){
            throw new RuntimeException("error");
        }
        Option option = optionalOption.get();
        if(isProductEnough(option.getAmount(), amount)){
            optionRepository.subtractById(optionId,amount);
        }
    }
    public boolean isProductEnough(int totalAmount, int purchaseAmount){
        if(totalAmount >= purchaseAmount){
            return true;
        }
        throw new RuntimeException("error");
    }
}
