package gift.entity;

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
@Table(name = "category")
public class Category {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Long Id;
    @Column
    String name;

    @OneToMany(mappedBy = "category")
    List<Product> products = new ArrayList<>();

    public Long getId() {
        return Id;
    }

    public String getName() {
        return name;
    }

    public List<Product> getProducts() {
        return products;
    }
}
