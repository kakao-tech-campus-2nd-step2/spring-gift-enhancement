package gift.core.domain.product;

import jakarta.annotation.Nullable;

public record Product(
        Long id,
        String name,
        Integer price,
        String imageUrl,
        ProductCategory category
) {
    public Product applyUpdate(
            @Nullable String name,
            @Nullable Integer price,
            @Nullable String imageUrl,
            @Nullable ProductCategory category
    ){
        return new Product(
                this.id(),
                name != null ? name : this.name(),
                price != null ? price : this.price(),
                imageUrl != null ? imageUrl : this.imageUrl(),
                category != null ? category : this.category()
        );
    }

    public Product withCategory(ProductCategory category){
        return new Product(
                this.id(),
                this.name(),
                this.price(),
                this.imageUrl(),
                category
        );
    }
}
