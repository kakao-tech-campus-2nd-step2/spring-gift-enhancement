package gift.domain;

import static gift.domain.validator.ProductOptionValidator.validatedRequestQuantity;

import gift.domain.base.BaseEntity;
import gift.domain.base.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class ProductOption extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer stock;

    private Long productId;

    protected ProductOption() {
    }

    public static class Builder extends BaseTimeEntity.Builder<Builder> {

        private String name;
        private Integer stock;
        private Long productId;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder stock(Integer stock) {
            this.stock = stock;
            return this;
        }

        public Builder productId(Long productId) {
            this.productId = productId;
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
        productId = builder.productId;
    }

    public ProductOption update(ProductOption option) {
        this.name = option.name;
        this.stock = option.stock;
        return this;
    }

    public ProductOption subtractQuantity(Integer quantity) {
        validatedRequestQuantity(stock, quantity);
        this.stock -= quantity;
        return this;
    }

    public String getName() {
        return name;
    }

    public Integer getStock() {
        return stock;
    }

    public Long getProductId() {
        return productId;
    }
}
