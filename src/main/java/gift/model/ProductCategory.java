package gift.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "product_category")
public class ProductCategory extends BaseEntity{
    @NotNull
    @Column(name = "name")
    private String name;
    @NotNull
    @Column(name = "image_url")
    private String imageUrl;
    @NotNull
    @Column(name="color")
    private String color;
    @NotNull
    @Column(name = "description")
    private String description;

    protected ProductCategory(){
    }
}
