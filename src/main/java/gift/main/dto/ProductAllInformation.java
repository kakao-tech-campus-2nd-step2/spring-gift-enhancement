package gift.main.dto;

import gift.main.entity.Product;

import java.util.List;

public record ProductAllInformation(
        Long id,
        String name,
        int price,
        String imageUrl,
        String seller,
        String categoryName,
        List<OptionResponse> optionResponses) {

    public ProductAllInformation(Product product, List<OptionResponse> optionResponses) {
        this(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getImageUrl(),
                product.getSellerName(),
                product.getCategoryName(),
                optionResponses);
    }

    public ProductAllInformation(ProductResponce productResponce, List<OptionResponse> optionResponses) {
        this(
                productResponce.id(),
                productResponce.name(),
                productResponce.price(),
                productResponce.imageUrl(),
                productResponce.seller(),
                productResponce.categoryName(),
                optionResponses);
    }
}
