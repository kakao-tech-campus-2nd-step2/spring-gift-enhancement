package gift.main.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false,unique = true)
    private int uniNumber;

    @Column(nullable = false,unique = true)
    private String name;

    @OneToMany(mappedBy = "category",fetch = FetchType.LAZY,  cascade = CascadeType.DETACH)
    private List<Product> Products;

    public long getId() {
        return id;
    }

    public int getUniNumber() {
        return uniNumber;
    }

    public String getName() {
        return name;
    }
}
