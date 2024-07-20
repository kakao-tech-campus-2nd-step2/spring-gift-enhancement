package gift.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;

@Entity
@Table(name = "products")
public class Products extends BaseEntity {

    @Column(nullable = false, length = 15)
    private String name;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false, name = "image_url")
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    public Products() {
    }

    public Products(String name, int price, String imageUrl, Category category) {
        super();
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.category = category;
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

    public Category getCategory() {
        return category;
    }

    public static class Builder {
        private Long id;
        private String name;
        private int price;
        private String imageUrl;
        private Category category;

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

        public Builder category(Category category) {
            this.category = category;
            return this;
        }

        public Products build() {
            Products product = new Products(name, price, imageUrl, category);
            product.id = this.id;
            return product;
        }
    }
}
