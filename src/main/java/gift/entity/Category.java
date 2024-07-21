package gift.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import gift.dto.CategoryRequestDTO;
import gift.dto.ProductRequestDTO;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name="name", unique=true)
    private String name;

    @Column(nullable = false, name="color")
    private String color;

    @Column(name="description")
    private String description;

    @Column(nullable = false, name="image_url")
    private String imageUrl;

    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "category", orphanRemoval = true)
    @JsonManagedReference
    private List<Product> products = new ArrayList<Product>();

    public Category(){

    }

    public Category(String name, String color, String description, String imageUrl) {
        this.name = name;
        this.color = color;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void updateCategory(CategoryRequestDTO categoryRequestDTO){
        this.name = categoryRequestDTO.name();
        this.color = categoryRequestDTO.color();
        this.description = categoryRequestDTO.description();
        this.imageUrl = categoryRequestDTO.imageUrl();
    }

    public void addProduct(ProductRequestDTO productRequestDTO){
        Product product = new Product(productRequestDTO.name(),
                productRequestDTO.price(),
                productRequestDTO.imageUrl(),
                this);
        this.products.add(product);
    }


}
