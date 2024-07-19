package gift.init;

import gift.domain.ProductOrder.decreaseProductOption;
import gift.service.ProductOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductOrderCreator {

    @Autowired
    private ProductOrderService productOrderService;

    public void ProductCreator() {
        productOrderService.decreaseProductOption(1L, 1L, new decreaseProductOption(1L));
        productOrderService.decreaseProductOption(1L, 2L, new decreaseProductOption(50L));

        productOrderService.decreaseProductOption(2L, 2L, new decreaseProductOption(50L));
    }
}
