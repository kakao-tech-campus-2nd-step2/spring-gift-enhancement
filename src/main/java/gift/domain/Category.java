package gift.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long category_id;

    @Column(nullable = false, unique = true)
    private String name;

    private CategoryType categoryType;

    @OneToMany(mappedBy = "category")
    private List<Product> products = new ArrayList<>();
}
