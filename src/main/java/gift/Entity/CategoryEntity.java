package gift.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private CategoryEntity parent;

    @JsonIgnore//json변환될 때 해당 필드 무시 -> 서로가 서로 참조 하는 경우 방지
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CategoryEntity> children = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductEntity> products = new ArrayList<>();


    public CategoryEntity() {
    }

    public CategoryEntity(String name, String code) {
        this.name = name;
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

    public CategoryEntity getParent() {
        return parent;
    }

    public void setParent(CategoryEntity parent) {
        this.parent = parent;
    }

    public List<CategoryEntity> getChildren() {
        return children;
    }

    public void setChildren(List<CategoryEntity> children) {
        this.children = children;
    }

    public void addChild(CategoryEntity child) {
        child.setParent(this);
        this.children.add(child);
    }

    public void removeChild(CategoryEntity child) {
        child.setParent(null);
        this.children.remove(child);
    }

    public List<ProductEntity> getProducts() {
        return products;
    }

    public void setProducts(List<ProductEntity> products) {
        this.products = products;
    }

    public void addProduct(ProductEntity product) {
        product.setCategory(this);
        this.products.add(product);
    }

    public void removeProduct(ProductEntity product) {
        product.setCategory(null);
        this.products.remove(product);
    }
}
