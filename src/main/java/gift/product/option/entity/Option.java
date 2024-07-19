package gift.product.option.entity;

import gift.exception.CustomException;
import gift.exception.ErrorCode;
import gift.product.entity.Product;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Entity
@Table(name = "options")
public class Option {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String name;

    @Column
    private Integer quantity;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "product_id", nullable = false,
        foreignKey = @ForeignKey(name = "fk_options_product_id_ref_products_id"))
    private Product product;

    public Option(Long id) {
        this.id = id;
    }

    public Option(String name, Integer quantity, Product product) {
        validateName(name);
        validateQuantity(quantity);
        this.product = product;
    }

    protected Option() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    private void validateName(String name) {
        Pattern pattern = Pattern.compile("^[\\w\\s()\\[\\]+\\-&/_]*$");
        Matcher matcher = pattern.matcher(name);
        if (!matcher.find() || name.length() > 50) {
            throw new CustomException(ErrorCode.INVALID_OPTION_NAME);
        }
        this.name = name;
    }

    private void validateQuantity(Integer quantity) {
        if (quantity < 1 || quantity >= 100_000_000) {
            throw new CustomException(ErrorCode.INVALID_OPTION_QUANTITY);
        }
        this.quantity = quantity;
    }

}
