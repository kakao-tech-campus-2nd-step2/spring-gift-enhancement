package gift.service;

import gift.constants.Messages;
import gift.domain.Option;
import gift.domain.Product;
import gift.dto.OptionRequestDto;
import gift.dto.OptionResponseDto;
import gift.exception.OptionNotFoundException;
import gift.exception.ProductNotFoundException;
import gift.repository.OptionRepository;
import gift.repository.ProductRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class OptionService {

    private final OptionRepository optionRepository;
    private final ProductRepository productRepository;

    public OptionService(OptionRepository optionRepository, ProductRepository productRepository) {
        this.optionRepository = optionRepository;
        this.productRepository = productRepository;
    }

    public void save(Long id, OptionRequestDto optionRequestDto) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new ProductNotFoundException(
                Messages.NOT_FOUND_PRODUCT_MESSAGE));
        nameValidate(product.getOptions(),optionRequestDto.getName());
        quantityValidate(optionRequestDto.getQuantity());
        Option option = new Option(optionRequestDto.getName(), optionRequestDto.getQuantity());
        option.setProduct(product);
        optionRepository.save(option);
        product.setOptions(option);
    }

    public List<OptionResponseDto> findAll(Long id) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new ProductNotFoundException(Messages.NOT_FOUND_PRODUCT_MESSAGE));
        return product.getOptions()
            .stream()
            .map(option -> new OptionResponseDto(option.getId(), option.getName(),
                option.getQuantity()))
            .collect(Collectors.toList());
    }

    public void deleteById(Long id) {
        Option option = optionRepository.findById(id).orElseThrow(()->new OptionNotFoundException(Messages.NOT_FOUND_OPTION_MESSAGE));
        optionRepository.deleteById(id);
        Product product = productRepository.findById(option.getProduct().getId()).orElseThrow(()-> new ProductNotFoundException(Messages.NOT_FOUND_PRODUCT_MESSAGE));
        optionValidate(product.getOptions());
        product.getOptions().remove(option);
        optionRepository.deleteById(id);
    }

    private void nameValidate(List<Option> options,String name){
        for(Option option:options){
            if(option.getName().equals(name)){
                throw new IllegalArgumentException(Messages.DUPLICATE_OPTION_NAME_MESSAGE);
            }
        }
    }

    private void quantityValidate(int quantity){
        if(quantity < 0 || quantity > 100000000){
            throw new IllegalArgumentException(Messages.QUANTITY_OUT_OF_RANGE_MESSAGE);
        }
    }

    private void optionValidate(List<Option> options){
        if(options.size() == 1){
            throw new IllegalArgumentException(Messages.OPTION_BELOW_MINIMUM_MESSAGE);
        }
    }

}
