package gift.domain;

import gift.domain.base.BaseEntity;
import gift.domain.base.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;

@Entity
public class ProductOption extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer stock;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    protected ProductOption() {
    }

    public static class Builder extends BaseTimeEntity.Builder<Builder> {

        private String name;
        private Integer stock;
        private Product product;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder stock(Integer stock) {
            this.stock = stock;
            return this;
        }

        public Builder product(Product product) {
            this.product = product;
            return this;
        }

        @Override
        protected Builder self() {
            return this;
        }

        @Override
        public ProductOption build() {
            return new ProductOption(this);
        }
    }

    private ProductOption(Builder builder) {
        super(builder);
        name = builder.name;
        stock = builder.stock;
    }

    public String getName() {
        return name;
    }

    public Integer getStock() {
        return stock;
    }

    public Product getProduct() {
        return product;
    }
}
