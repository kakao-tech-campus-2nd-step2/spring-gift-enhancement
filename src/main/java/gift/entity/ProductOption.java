package gift.entity;

import gift.exception.BusinessException;
import gift.exception.ErrorCode;
import jakarta.persistence.*;

@Entity
@Table(name = "product_option")
public class ProductOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false, foreignKey = @ForeignKey(name = "fk_product_option_product"), columnDefinition = "BIGINT NOT NULL COMMENT 'Foreign Key to Product'")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_id", nullable = false, foreignKey = @ForeignKey(name = "fk_product_option_option"), columnDefinition = "BIGINT NOT NULL COMMENT 'Foreign Key to Option'")
    private Option option;

    @Column(nullable = false, columnDefinition = "INT NOT NULL COMMENT 'Option Quantity'")
    private int quantity;

    protected ProductOption() {
    }

    public ProductOption(Product product, Option option, int quantity) {
        validateQuantity(quantity);
        this.product = product;
        this.option = option;
        this.quantity = quantity;
    }

    public void updateQuantity(int quantity) {
        validateQuantity(quantity);
        this.quantity = quantity;
    }

    private void validateQuantity(int quantity) {
        if (quantity < 1 || quantity >= 100000000) {
            throw new BusinessException(ErrorCode.INVALID_QUANTITY);
        }
    }

    public Long getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public Option getOption() {
        return option;
    }

    public int getQuantity() {
        return quantity;
    }
}
