package gift.dto;

import gift.entity.Product;

public class WishRequest {

    private Product product;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

}
