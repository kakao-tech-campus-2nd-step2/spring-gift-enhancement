package gift.product.service.facade;

import gift.common.annotation.Facade;
import gift.product.service.ProductOptionService;
import gift.product.service.ProductService;
import gift.product.service.command.ProductCommand;
import gift.product.service.command.ProductOptionCommand;
import jakarta.transaction.Transactional;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Facade
public class ProductFacade {
    private static final Logger log = LoggerFactory.getLogger(ProductFacade.class);
    private final ProductService productService;
    private final ProductOptionService productOptionService;

    public ProductFacade(ProductService productService, ProductOptionService productOptionService) {
        this.productService = productService;
        this.productOptionService = productOptionService;
    }

    @Transactional
    public Long saveProduct(ProductCommand productCommand, List<ProductOptionCommand> productOptionCommands) {
        var productId = productService.saveProduct(productCommand);
        log.info("Product Created");
        productOptionService.createProductOptions(productId, productOptionCommands);
        return productId;
    }
}
