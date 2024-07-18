package gift.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, name="name")
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
    private List<Product> products;

}
