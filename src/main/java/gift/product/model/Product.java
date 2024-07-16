package gift.product.model;

import gift.common.exception.ProductException;
import gift.common.model.BaseEntity;
import gift.product.ProductErrorCode;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Product extends BaseEntity {

    @Column(name = "name", nullable = false, length = 15)
    private String name;
    @Column(name = "price", nullable = false)
    private Integer price;
    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    protected Product() {
    }

    public Product(String name, int price, String imageUrl) {
        validateKakaoWord(name);
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void updateInfo(Product product) {
        this.name = product.getName();
        this.price = product.getPrice();
        this.imageUrl = product.getImageUrl();
    }

    private void validateKakaoWord(String name) throws ProductException {
        if (name.contains("카카오")) {
            throw new ProductException(ProductErrorCode.HAS_KAKAO_WORD);
        }
    }
}
