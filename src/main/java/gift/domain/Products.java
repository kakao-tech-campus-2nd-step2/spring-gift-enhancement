package gift.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;


@Entity
@Table(name = "products")
public class Products extends BaseEntity {

    @Column(nullable = false, length = 15)
    private String name;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false, name = "image_url")
    private String imageUrl;

    public Products() {
    }

    public Products(String name, int price, String imageUrl) {
        super();
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
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

    public static class Builder {
        private Long id;
        private String name;
        private int price;
        private String imageUrl;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder price(int price) {
            this.price = price;
            return this;
        }

        public Builder imageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public Products build() {
            Products product = new Products(name, price, imageUrl);
            product.id = this.id;
            return product;
        }
    }
}
