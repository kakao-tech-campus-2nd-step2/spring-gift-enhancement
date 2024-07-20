package gift.service.facade;

import gift.dto.ProductRequest;
import gift.dto.ProductResponse;
import gift.model.MemberRole;
import gift.service.OptionService;
import gift.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProductAndOptionFacade {

    private final ProductService productService;
    private final OptionService optionService;


    public ProductAndOptionFacade(ProductService productService, OptionService optionService) {
        this.productService = productService;
        this.optionService = optionService;
    }

    public ProductResponse addProduct(ProductRequest productRequest, MemberRole memberRole) {
        var product = productService.addProduct(productRequest, memberRole);
        optionService.makeDefaultOption(product);
        return product;
    }
}
