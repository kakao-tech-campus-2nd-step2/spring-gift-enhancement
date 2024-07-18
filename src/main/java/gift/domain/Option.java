package gift.domain;

import gift.entity.OptionEntity;
import gift.entity.ProductEntity;


public class Option {


    private Long id;


    private String name;


    private Long quantity;


    private Long productId;

    public Option() {

    }

    public Option(String name, Long quantity, Long productId) {
        this.name = name;
        this.quantity = quantity;
        this.productId = productId;
    }

    public Option(Long id, String name, Long quantity, Long productId) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.productId = productId;
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

    public Long getProductId() {
        return productId;
    }

    public void setProductEntity(Long productId) {
        this.productId = productId;
    }

}
