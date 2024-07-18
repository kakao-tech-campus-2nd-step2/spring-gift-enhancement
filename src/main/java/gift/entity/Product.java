package gift.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "product")
public class Product {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String url;
    @Column(nullable = false)
    private Integer price;

    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category;

    public Product() {
    }

    public Product(String name, Integer price, String url, Category category) {
        this.name = name;
        this.price = price;
        this.url = url;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public String getUrl() {
        return url;
    }

    public Category getCategory() {
        return category;
    }

    public void update(String name, Integer price, String url, Category category) {
        this.name = name;
        this.price = price;
        this.url = url;
        this.category = category;
    }

}
