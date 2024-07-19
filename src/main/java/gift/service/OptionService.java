package gift.service;

import gift.DTO.Option.OptionRequest;
import gift.DTO.Option.OptionResponse;
import gift.domain.Option;
import gift.domain.Product;
import gift.repository.OptionRepository;
import gift.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OptionService {
    private final OptionRepository optionRepository;
    private final ProductRepository productRepository;

    public OptionService(OptionRepository optionRepository, ProductRepository productRepository){
        this.optionRepository = optionRepository;
        this.productRepository = productRepository;
    }

    /*
     * 새로운 옵션을 저장하는 로직
     */
    @Transactional
    public void save(Long product_id, OptionRequest optionRequest){
        Option option = optionRepository.save(new Option(
                optionRequest.getName(),
                optionRequest.getQuantity()
        ));

        Product product = productRepository.findById(product_id).orElseThrow(NoSuchFieldError::new);
        product.getOptions().add(option);
    }
    /*
     * 한 상품의 옵션을 모두 가져오는 로직
     */
    public List<OptionResponse> findOneProductOption(Long product_id){
        Product product = productRepository.findById(product_id).orElseThrow(NoSuchFieldError::new);
        List<OptionResponse> answer = new ArrayList<>();
        List<Option> options = product.getOptions();

        for (Option option : options) {
            answer.add(new OptionResponse(option));
        }
        return answer;
    }
    /*
     * 옵션을 수정하는 로직
     */
    @Transactional
    public void update(Long option_id, OptionRequest optionRequest){
        Option option = optionRepository.findById(option_id).orElseThrow(NoSuchFieldError::new);
        option.update(optionRequest.getName(), optionRequest.getQuantity());
    }
    /*
     * 옵션을 삭제하는 로직
     */
    @Transactional
    public void delete(Long id){
        optionRepository.deleteById(id);
    }
}
