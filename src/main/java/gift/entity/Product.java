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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int price;

    @Column(name = "img_url", nullable = false)
    private String imgUrl;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    public Product() {
    }

    public Product(String name, int price, String imgUrl, Category category) {
        this.name = name;
        this.price = price;
        this.imgUrl = imgUrl;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void updateId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void updateName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void updatePrice(int price) {
        this.price = price;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void updateImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Category getCategory() {
        return category;
    }

    public void updateCategory(Category category) {
        this.category = category;
    }
}
