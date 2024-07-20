package gift.service;

import gift.constants.Messages;
import gift.domain.Option;
import gift.domain.Product;
import gift.dto.request.OptionRequest;
import gift.dto.response.OptionResponse;
import gift.dto.response.ProductResponse;
import gift.exception.CannotDeleteLastOptionException;
import gift.exception.DuplicateOptionNameException;
import gift.exception.OptionNotFoundException;
import gift.exception.ProductNotFoundException;
import gift.repository.OptionRepository;
import gift.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class OptionService {
    public final OptionRepository optionRepository;
    public final ProductRepository productRepository;

    public OptionService(OptionRepository optionRepository, ProductRepository productRepository) {
        this.optionRepository = optionRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public void save(Long productId, OptionRequest optionRequest){
        if(optionRepository.existsByName(optionRequest.name())) {
            throw new DuplicateOptionNameException(Messages.OPTION_NAME_ALREADY_EXISTS);
        }
        Product foundProduct = productRepository.findById(productId)
                .orElseThrow(()->  new ProductNotFoundException(Messages.NOT_FOUND_PRODUCT_BY_NAME));
        optionRepository.save(new Option(optionRequest.name(), optionRequest.quantity(), foundProduct));
    }

    public OptionResponse findById(Long id){
        return optionRepository.findById(id)
                .map(OptionResponse::from)
                .orElseThrow(()-> new OptionNotFoundException(Messages.NOT_FOUND_OPTION));
    }

    public List<OptionResponse> findByProductId(Long productId){
        return optionRepository.findByProductId(productId)
                .stream()
                .map(OptionResponse::from)
                .toList();
    }

    public List<OptionResponse> findAll(){
        return optionRepository.findAll()
                .stream()
                .map(OptionResponse::from)
                .toList();
    }

    public void deleteById(Long id){
        Option foundOption = optionRepository.findById(id)
                .orElseThrow(()->new OptionNotFoundException(Messages.NOT_FOUND_OPTION));

        int productOptionCount = foundOption.getProduct().getOptions().size();

        if(productOptionCount < 2){
            throw new CannotDeleteLastOptionException(Messages.CANNOT_DELETE_LAST_OPTION);
        }

        foundOption.remove();
        optionRepository.deleteById(id);
    }

    public void updateById(Long id, OptionRequest optionRequest){
        Option foundOption = optionRepository.findById(id)
                .orElseThrow(()->new OptionNotFoundException(Messages.NOT_FOUND_OPTION));

        foundOption.updateOption(optionRequest.name(),optionRequest.quantity(),foundOption.getProduct());
        optionRepository.save(foundOption);
    }

    public void subtractQuantityById(Long id, int quantity){
        Option foundOption = optionRepository.findById(id)
                .orElseThrow(()->new OptionNotFoundException(Messages.NOT_FOUND_OPTION));
        foundOption.subtract(quantity);
        optionRepository.save(foundOption);
    }
}
