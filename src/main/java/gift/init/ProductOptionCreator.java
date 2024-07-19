package gift.init;

import gift.domain.ProductOption.CreateOption;
import gift.service.ProductOptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductOptionCreator {

    @Autowired
    private ProductOptionService productOptionService;

    public void ProductOptionCreator() {
        productOptionService.createProductOption(1L, new CreateOption("option2", 100L));
        productOptionService.createProductOption(2L, new CreateOption("option2", 100L));
        productOptionService.createProductOption(3L, new CreateOption("option2", 100L));

        productOptionService.createProductOption(2L, new CreateOption("option3", 100L));
        productOptionService.createProductOption(3L, new CreateOption("option3", 100L));
    }
}
