package gift.option;

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
}
