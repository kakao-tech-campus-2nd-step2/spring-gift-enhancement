package gift.entity;

import gift.domain.CategoryDTO;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;

@Entity
@Table(name = "category")
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, length = 7)
    private String color;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    private String description;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY,
        cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductEntity> productEntities;

    public CategoryEntity() {}

    public CategoryEntity(String name, String color, String imageUrl, String description) {
        this.name = name;
        this.color = color;
        this.imageUrl = imageUrl;
        this.description = description;
    }

    public CategoryEntity(CategoryDTO categoryDTO) {
        this.id = categoryDTO.getId();
        this.name = categoryDTO.getName();
        this.color = categoryDTO.getColor();
        this.imageUrl = categoryDTO.getImageUrl();
        this.description = categoryDTO.getDescription();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setId(Long id) {this.id = id;}

    public void setName(String name) {this.name = name;}

    public void setColor(String Color) {this.color = color;}

    public void setImageUrl(String imageUrl) {this.imageUrl = imageUrl;}

    public void setDescription(String description) {this.description = description;}

    public static CategoryDTO toDTO(CategoryEntity category) {
        return new CategoryDTO(category.getId(), category.getName(), category.getColor(), category.getImageUrl(), category.getDescription());
    }

    public void update(CategoryDTO categoryDTO) {
        this.setName(categoryDTO.getName());
        this.setColor(categoryDTO.getColor());
        this.setImageUrl(categoryDTO.getImageUrl());
        this.setDescription(categoryDTO.getDescription());
    }

}
