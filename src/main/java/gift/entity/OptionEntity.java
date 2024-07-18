package gift.entity;

import gift.domain.Option;
import jakarta.persistence.*;

@Entity
@Table(name = "options")
public class OptionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "quantity", nullable = false)
    private Long quantity;

    public OptionEntity() {

    }

    public OptionEntity(String name, Long quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public OptionEntity(Long id, String name, Long quantity) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }


    public static Option toDto(OptionEntity optionEntity) {
        return new Option(
            optionEntity.getId(),
            optionEntity.getName(),
            optionEntity.getQuantity()
        );
    }

}
