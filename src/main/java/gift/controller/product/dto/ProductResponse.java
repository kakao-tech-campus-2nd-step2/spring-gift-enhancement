package gift.controller.product.dto;

import gift.controller.product.dto.OptionResponse.Info;
import gift.model.product.Product;
import gift.service.product.dto.OptionModel;
import gift.service.product.dto.ProductModel;
import java.util.List;

public class ProductResponse {

    public record Info(
        Long productId,
        String name,
        Integer price,
        String imageUrl,
        String category,
        OptionResponse.InfoList optionInfos
    ) {

        public static Info from(ProductModel.Info productModel,
            List<OptionModel.Info> optionModels) {
            var optionInfos = OptionResponse.InfoList.from(optionModels);
            return new Info(
                productModel.id(),
                productModel.name(),
                productModel.price(),
                productModel.imageUrl(),
                productModel.category(),
                optionInfos
            );
        }
    }
}
