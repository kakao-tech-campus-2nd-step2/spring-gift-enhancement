package gift.model.option;

import gift.model.item.Item;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;

@Entity
public class Option {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Column
    private String name;
    @NotNull
    @Column
    private Long quantity;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    protected Option() {
    }

    public Option(Long id, String name, Long quantity, Item item) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.item = item;
    }

    public Option(String name, Long quantity, Item item) {
        this(null, name, quantity, item);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getQuantity() {
        return quantity;
    }

    public Item getItem() {
        return item;
    }

    public OptionDTO toDTO() {
        return new OptionDTO(id, name, quantity);
    }

    public boolean update(String name, Long quantity) {
        if (quantity < 1 || quantity > 100_000_000) {
            return false;
        }
        this.name = name;
        this.quantity = quantity;
        return true;
    }
}