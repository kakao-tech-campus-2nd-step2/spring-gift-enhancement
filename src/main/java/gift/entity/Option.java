package gift.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "option")
public class Option {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int quantity;

    public Option() {
    }

    public Option(OptionDTO optionDTO) {
        this.name = optionDTO.getName();
        this.quantity = optionDTO.getQuantity();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setOptionDTO(OptionDTO optionDTO) {
        this.name = optionDTO.getName();
        this.quantity = optionDTO.getQuantity();
    }
}
