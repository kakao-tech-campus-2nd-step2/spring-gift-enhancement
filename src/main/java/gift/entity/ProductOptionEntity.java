package gift.entity;

import jakarta.persistence.*;

/**
 * 제품 옵션 엔티티. 데이터베이스 테이블과 매핑된다.
 */
@Entity(name = "product_option")
public class ProductOptionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private long quantity;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity productEntity;

    protected ProductOptionEntity() {
    }

    public ProductOptionEntity(Long id, String name, long quantity, ProductEntity productEntity) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.productEntity = productEntity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public ProductEntity getProductEntity() {
        return productEntity;
    }

    public void setProductEntity(ProductEntity productEntity) {
        this.productEntity = productEntity;
    }
}
