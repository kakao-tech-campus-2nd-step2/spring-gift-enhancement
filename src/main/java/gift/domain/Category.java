package gift.domain;

import gift.response.CategoryResponse;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Category {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> products = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void changeName(String name) {
        this.name = name;
    }

    public CategoryResponse toDto() {
        return new CategoryResponse(this.id, this.name);
    }

    public Category(String name) {
        this.name = name;
    }

    public Category() {
    }

    public Long getId() {
        return id;
    }

}
