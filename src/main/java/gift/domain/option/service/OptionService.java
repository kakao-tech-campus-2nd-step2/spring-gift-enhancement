package gift.domain.option.service;

import gift.domain.option.dto.OptionRequest;
import gift.domain.option.dto.OptionResponse;
import gift.domain.option.entity.Option;
import gift.domain.option.exception.OptionNotFoundException;
import gift.domain.option.repository.OptionRepository;
import gift.domain.product.entity.Product;
import gift.domain.product.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OptionService {
    private final OptionRepository optionRepository;
    private final ProductRepository productRepository;

    public OptionService(OptionRepository optionRepository, ProductRepository productRepository) {
        this.optionRepository = optionRepository;
        this.productRepository = productRepository;
    }

    public List<OptionResponse> getProductOptions(Long id) {
        Product savedProduct = productRepository.findById(id).orElseThrow(()-> new OptionNotFoundException("[상품 옵션 조회] 찾는 상품이 없습니다."));
        List<Option> savedOptions = optionRepository.findAllByProduct(savedProduct);
        return savedOptions.stream().map((option)-> new OptionResponse(option.getId(), option.getName(), option.getQuantity())).toList();
    }
    public OptionResponse addOptionToProduct(Long id, OptionRequest request){

        Product savedProduct = productRepository.findById(id).orElseThrow();

        List<Option> savedOptions = optionRepository.findAllByProduct(savedProduct);
        Option newOption = dtoToEntity(request);
        newOption.checkDuplicateName(savedOptions);
        newOption.addProduct(savedProduct);
        Option savedOption = optionRepository.save(newOption);

        // 리턴 값 생각
        return entityToDto(savedOption);
    }

    private OptionResponse entityToDto(Option option){
        return new OptionResponse(option.getId(), option.getName(), option.getQuantity());
    }

    private Option dtoToEntity(OptionRequest request){
        return new Option(request.getName(), request.getQuantity());
    }

}
