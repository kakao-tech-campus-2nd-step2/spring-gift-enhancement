package gift.entity;

import gift.domain.Option;
import gift.domain.Product;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

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

    @ManyToOne(targetEntity = ProductEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private ProductEntity productEntity;

    public OptionEntity() {

    }

    public OptionEntity(String name, Long quantity, ProductEntity productEntity) {
        this.name = name;
        this.quantity = quantity;
        this.productEntity = productEntity;
    }

    public OptionEntity(Long id, String name, Long quantity, ProductEntity productEntity) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.productEntity = productEntity;
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

    public ProductEntity getProductEntity() {
        return productEntity;
    }

    public void setProductEntity(ProductEntity productEntity) {
        this.productEntity = productEntity;
    }

    public static Option toDto(OptionEntity optionEntity) {
        return new Option(
            optionEntity.getId(),
            optionEntity.getName(),
            optionEntity.getQuantity(),
            optionEntity.getProductEntity().getId()
        );
    }

}
