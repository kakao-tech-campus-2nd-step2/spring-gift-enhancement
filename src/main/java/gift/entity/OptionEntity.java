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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false, foreignKey = @ForeignKey(name = "fk_options_product_id_refer_products_id", foreignKeyDefinition = "FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE"))
    private ProductEntity productEntity;

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

    public void setProductEntity(ProductEntity productEntity) {
        this.productEntity = productEntity;
    }


    public static Option toDto(OptionEntity optionEntity) {
        return new Option(
            optionEntity.getId(),
            optionEntity.getName(),
            optionEntity.getQuantity()
        );
    }

}
