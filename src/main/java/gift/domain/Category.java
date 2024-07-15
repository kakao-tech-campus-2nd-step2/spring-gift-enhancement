package gift.domain;

import gift.controller.dto.CategoryRequestDTO;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categorie")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    String color;

    @Column(nullable = false)
    String imageUrl;

    @Column(nullable = false)
    String description;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductCategory> productCategory = new ArrayList<>();

    public Category() {
    }

    public Category(String name, String color, String imageUrl, String description) {
        this.name = name;
        this.color = color;
        this.imageUrl = imageUrl;
        this.description = description;
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void addProductCategory(ProductCategory productCategory) {
        this.productCategory.add(productCategory);
        productCategory.setCategories(this);
    }

    public void removeProductCategory(ProductCategory productCategory) {
        this.productCategory.remove(productCategory);
        productCategory.setCategories(null);
    }

    public void updateCategory(CategoryRequestDTO categoryRequestDTO){
        this.name = categoryRequestDTO.getName();
        this.color = categoryRequestDTO.getColor();
        this.description = categoryRequestDTO.getDescription();
        this.imageUrl = categoryRequestDTO.getImageUrl();
    }


}
