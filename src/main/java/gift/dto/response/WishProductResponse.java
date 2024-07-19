package gift.dto.response;

import gift.domain.Product;

public class WishProductResponse {

    private String name;
    private int price;
    private String imageUrl;

    public WishProductResponse(Product product) {
        this.name = product.getName();
        this.price = product.getPrice();
        this.imageUrl = product.getImageUrl();
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
