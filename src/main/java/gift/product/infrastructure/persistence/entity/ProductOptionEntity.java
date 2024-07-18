package gift.product.infrastructure.persistence.entity;

import gift.core.BaseEntity;
import gift.core.domain.product.ProductOption;
import jakarta.persistence.*;

@Entity
@Table(name = "product_option", indexes = {
        @Index(name = "product_option_product_id_idx", columnList = "product_id")
})
public class ProductOptionEntity extends BaseEntity {
    @Column(name = "product_id")
    private Long productId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    public ProductOptionEntity() {
    }

    public ProductOptionEntity(Long productId, Long optionId, String name, Integer quantity) {
        super(optionId);
        this.productId = productId;
        this.name = name;
        this.quantity = quantity;
    }

    public ProductOptionEntity(Long productId, String name, Integer quantity) {
        this.productId = productId;
        this.name = name;
        this.quantity = quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public ProductOption toDomain() {
        return new ProductOption(getId(), name, quantity);
    }

    public static ProductOptionEntity fromDomain(Long productId, ProductOption option) {
        return new ProductOptionEntity(productId, option.id(), option.name(), option.quantity());
    }
}
