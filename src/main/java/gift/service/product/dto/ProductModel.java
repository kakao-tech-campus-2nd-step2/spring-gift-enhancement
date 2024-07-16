package gift.service.product.dto;

import gift.model.product.Product;

public class ProductModel {

    public record Info(
        Long id,
        String name,
        Integer price,
        String imageUrl,
        String category
    ) {

        public static Info from(Product product) {
            return new Info(product.getId(), product.getName(), product.getPrice(),
                product.getImageUrl(), product.getCategory().getName());
        }
    }

}
