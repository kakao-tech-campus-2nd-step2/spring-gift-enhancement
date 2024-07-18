package gift.service;

import gift.constants.Messages;
import gift.domain.Option;
import gift.dto.request.OptionRequest;
import gift.dto.response.OptionResponse;
import gift.dto.response.ProductResponse;
import gift.exception.CannotDeleteLastOptionException;
import gift.exception.DuplicateOptionNameException;
import gift.exception.OptionNotFoundException;
import gift.repository.OptionRepository;
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
    public void save(OptionRequest optionRequest){
        if(optionRepository.existsByName(optionRequest.name())) {
            throw new DuplicateOptionNameException(Messages.OPTION_NAME_ALREADY_EXISTS);
        }
        ProductResponse productResponse = productService.findById(optionRequest.productId());
        optionRepository.save(new Option(optionRequest.name(), optionRequest.quantity(), productResponse.toEntity()));
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

        ProductResponse foundProductResponse = productService.findById(optionRequest.productId());
        foundOption.updateOption(optionRequest.name(),optionRequest.quantity(),foundProductResponse.toEntity());
        optionRepository.save(foundOption);
    }
}
