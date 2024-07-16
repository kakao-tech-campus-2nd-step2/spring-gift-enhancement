package gift.dto.response;

import gift.domain.Product;

public record ProductResponseDto(Long id, String name, int price, String imageUrl) {
    public static ProductResponseDto from(final Product product){
        return new ProductResponseDto(product.getId(), product.getName(), product.getPrice(), product.getImageUrl());
    }

    public Product toEntity(){
        return new Product(this.id, this.name, this.price, this.imageUrl);
    }
}
