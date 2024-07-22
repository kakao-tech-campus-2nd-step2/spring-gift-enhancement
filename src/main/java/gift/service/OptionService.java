package gift.service;

import gift.constants.Messages;
import gift.domain.Option;
import gift.domain.Product;
import gift.dto.request.OptionRequest;
import gift.dto.response.OptionResponse;
import gift.dto.response.ProductResponse;
import gift.exception.DuplicateOptionNameException;
import gift.exception.OptionNotFoundException;
import gift.exception.ProductNotFoundException;
import gift.repository.OptionRepository;
import gift.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OptionService {
    public final OptionRepository optionRepository;
    public final ProductService productService;

    public OptionService(OptionRepository optionRepository, ProductService productService) {
        this.optionRepository = optionRepository;
        this.productService = productService;
    }

    @Transactional
    public void save(Long productId, OptionRequest optionRequest){
        if(optionRepository.existsByName(optionRequest.name())) {
            throw new DuplicateOptionNameException(Messages.OPTION_NAME_ALREADY_EXISTS);
        }
        ProductResponse foundProduct = productService.findById(productId);
        optionRepository.save(new Option(optionRequest.name(), optionRequest.quantity(), foundProduct.toEntity()));
    }

    @Transactional(readOnly = true)
    public OptionResponse findById(Long id){
        return optionRepository.findById(id)
                .map(OptionResponse::from)
                .orElseThrow(()-> new OptionNotFoundException(Messages.NOT_FOUND_OPTION));
    }

    @Transactional(readOnly = true)
    public List<OptionResponse> findByProductId(Long productId){
        return optionRepository.findByProductId(productId)
                .stream()
                .map(OptionResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<OptionResponse> findAll(){
        return optionRepository.findAll()
                .stream()
                .map(OptionResponse::from)
                .toList();
    }

    @Transactional
    public void deleteById(Long id){
        Option foundOption = optionRepository.findById(id)
                .orElseThrow(()->new OptionNotFoundException(Messages.NOT_FOUND_OPTION));
        foundOption.remove();
        optionRepository.deleteById(id);
    }

    @Transactional
    public void updateById(Long id, OptionRequest optionRequest){
        Option foundOption = optionRepository.findById(id)
                .orElseThrow(()->new OptionNotFoundException(Messages.NOT_FOUND_OPTION));

        foundOption.updateOption(optionRequest.name(),optionRequest.quantity(),foundOption.getProduct());
    }

    @Transactional
    public void subtractQuantityById(Long id, int quantity){
        Option foundOption = optionRepository.findById(id)
                .orElseThrow(()->new OptionNotFoundException(Messages.NOT_FOUND_OPTION));
        foundOption.subtract(quantity);
    }
}
