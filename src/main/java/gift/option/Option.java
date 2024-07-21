package gift.option;

import static gift.exception.ErrorMessage.OPTION_NAME_ALLOWED_CHARACTER;
import static gift.exception.ErrorMessage.OPTION_NAME_LENGTH;
import static gift.exception.ErrorMessage.OPTION_QUANTITY_SIZE;
import static gift.exception.ErrorMessage.OPTION_SUBTRACT_NOT_ALLOWED_NEGATIVE_NUMBER;

import gift.product.Product;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Option {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private int quantity;

    @ManyToOne
    @JoinColumn(name = "product")
    private Product product;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    protected Option() {

    }

    public Option(long id, String name, int quantity, Product product) {
        validate(name, quantity);

        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.product = product;
    }

    public void update(String name, int quantity) {
        validate(name, quantity);

        this.name = name;
        this.quantity = quantity;
    }

    public void subtract(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException(OPTION_SUBTRACT_NOT_ALLOWED_NEGATIVE_NUMBER);
        }

        update(name, this.quantity - quantity);
    }

    private void validate(String name, int quantity) {
        validateName(name);
        validateQuantity(quantity);
    }

    private void validateName(String name) {
        if (!name.matches("^[a-zA-Z0-9가-힣\s()\\[\\]+\\-&/_]*$")) {
            throw new IllegalArgumentException(OPTION_NAME_ALLOWED_CHARACTER);
        }

        if (name.length() > 50) {
            throw new IllegalArgumentException(OPTION_NAME_LENGTH);
        }
    }

    private void validateQuantity(int quantity) {
        if (quantity < 1 || quantity >= 100_000_000) {
            throw new IllegalArgumentException(OPTION_QUANTITY_SIZE);
        }
    }

    @Override
    public int hashCode() {
        return (int) id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Option option) {
            return id == option.id;
        }
        return false;
    }
}
