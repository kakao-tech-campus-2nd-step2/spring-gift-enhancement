package gift.Service;

import gift.Model.Option;
import gift.Model.Product;
import gift.Repository.OptionRepository;
import gift.Repository.ProductRepository;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class OptionService {
    private final OptionRepository optionRepository;
    private final ProductRepository productRepository;

    public OptionService(OptionRepository optionRepository, ProductRepository productRepository){
        this.optionRepository = optionRepository;
        this.productRepository = productRepository;
    }

    public List<Option> getAllOptions(Long productId){
        return optionRepository.findAllById(productId);
    }

    public Option addOption(Option option, Long productId){
        Product product = productRepository.findProductById(productId);
        Option newOption = new Option(option.getId(), product, option.getName(), option.getQuantity());
        return optionRepository.save(newOption);
    }

    public Option updateOption(Option option, Long productId, Long optionId){
        Product product = productRepository.findProductById(productId);
        Option newOption = new Option(optionId, product, option.getName(), option.getQuantity());
        return optionRepository.save(newOption);
    }

    public Option deleteOption(Long productId, Long optionId){
        Option deletedOption = optionRepository.findOptionById(optionId);
        optionRepository.deleteById(optionId);
        return deletedOption;
    }

    public int subtractQuantity(Long productId, Long optionId, int count){
        Option option = optionRepository.findOptionById(optionId);
        int optionQuantity = option.subtract(count);
        if(optionQuantity == 0 ){
            this.deleteOption(productId,optionId);
        }
        return optionQuantity;
    }
}
