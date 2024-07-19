package gift.option;

import static gift.exception.ErrorMessage.OPTION_NAME_ALLOWED_CHARACTER;
import static gift.exception.ErrorMessage.OPTION_NAME_LENGTH;
import static gift.exception.ErrorMessage.OPTION_QUANTITY_SIZE;

import gift.product.Product;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.util.Optional;

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
        if (!name.matches("^[a-zA-Z0-9가-힣\s()\\[\\]+\\-&/_]*$")) {
            throw new IllegalArgumentException(OPTION_NAME_ALLOWED_CHARACTER);
        }

        if (name.length() > 50) {
            throw new IllegalArgumentException(OPTION_NAME_LENGTH);
        }

        if (quantity < 1 || quantity >= 100_000_000) {
            throw new IllegalArgumentException(OPTION_QUANTITY_SIZE);
        }

        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.product = product;
    }

    public void updateOption(Option option) {
        Optional.ofNullable(option.name)
            .ifPresent(updateName -> this.name = updateName);
        Optional.of(option.quantity)
            .ifPresent(updateQuantity -> this.quantity = updateQuantity);
        Optional.ofNullable(option.product)
            .ifPresent(updateProduct -> this.product = updateProduct);
    }

    public void updateOption(OptionDTO optionDTO) {
        updateOption(
            new Option(
                optionDTO.getId(),
                optionDTO.getName(),
                optionDTO.getQuantity(),
                product
            )
        );
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
