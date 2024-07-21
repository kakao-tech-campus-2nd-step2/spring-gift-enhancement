package gift.Service;

import gift.Exception.OptionNotFoundException;
import gift.Exception.ProductNotFoundException;
import gift.Model.Option;
import gift.Model.Product;
import gift.Model.RequestOption;
import gift.Model.ResponseOptionDTO;
import gift.Repository.OptionRepository;
import gift.Repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class OptionService {
    private final OptionRepository optionRepository;
    private final ProductRepository productRepository;
    private final OptionKeeperService optionKeeperService;

    OptionService(OptionRepository optionRepository, ProductRepository productRepository, OptionKeeperService optionKeeperService){
        this.optionRepository = optionRepository;
        this.productRepository = productRepository;
        this.optionKeeperService= optionKeeperService;
    }

    @Transactional
    public Option addOption(Long productId, RequestOption requestOption){
        Product product = productRepository.findById(productId).orElseThrow(()->new ProductNotFoundException("매칭되는 product가 없습니다"));
        optionKeeperService.checkUniqueOptionName(product, requestOption.name());
        Option option = new Option(requestOption.name(), requestOption.quantity(), product);
        return optionRepository.save(option);
    }

    @Transactional(readOnly = true)
    public  List<ResponseOptionDTO> getOptions (Long productId){
        Product product = productRepository.findById(productId).orElseThrow(()-> new ProductNotFoundException("매칭되는 product가 없습니다"));
        return optionRepository.findByProduct(product)
                .stream()
                .map(it-> new ResponseOptionDTO(
                        it.getId(),
                        it.getName(),
                        it.getQuantity()))
                .toList();
    }

    @Transactional
    public void editOption(Long productId, Long optionId, RequestOption requestOption) {
        Option option = optionRepository.findById(optionId).orElseThrow(()-> new OptionNotFoundException("매칭되는 옵션이 없습니다"));
        if (!(option.isBelongToProduct(productId)))
            throw new OptionNotFoundException("해당 옵션은 해당 상품에 속하지 않는 옵션입니다");
        if(!(option.hasSameName(requestOption.name()))) //옵션의 name을 변경하려고 한다면 이미 존재하는 name이 있는지 체크
            optionKeeperService.checkUniqueOptionName(option.getProduct(), requestOption.name());
        option.update(requestOption.name(), requestOption.quantity());
    }

    @Transactional
    public void subtractQuantity(Long productId, Long optionId, int quantity) {
        Option option = optionRepository.findById(optionId).orElseThrow(() -> new OptionNotFoundException("매칭되는 옵션이 없습니다"));
        if (!(option.isBelongToProduct(productId)))
            throw new OptionNotFoundException("해당 옵션은 해당 상품에 속하지 않는 옵션입니다");
        optionKeeperService.checkOptionQuantity(option.getQuantity(), quantity);
        option.subtract(quantity);
    }

    @Transactional
    public void deleteOption(Long productId, Long optionId) {
        Option option = optionRepository.findById(optionId).orElseThrow(() -> new OptionNotFoundException("매칭되는 옵션이 없습니다"));
        if (!(option.isBelongToProduct(productId)))
            throw new OptionNotFoundException("해당 옵션은 해당 상품에 속하지 않는 옵션입니다");
        optionKeeperService.checkHasAtLeastOneOption(option.getProduct());
        optionRepository.delete(option);
    }
}
