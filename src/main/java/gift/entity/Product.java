package gift.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "name", nullable = false, length = 15)
    private String name;

    @Column(name = "img_url", nullable = false)
    private String imgUrl;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Wish> wishes;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    protected Product() {}

    public Product(String name, Integer price, String imgUrl, Category category) {
        this.name = name;
        this.price = price;
        this.imgUrl = imgUrl;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public Category getCategory() { return category; }

    public List<Wish> getWishes() { return wishes; }

    public void update(String name, Integer price, String imgUrl, Category category) {
        this.name = name;
        this.price = price;
        this.imgUrl = imgUrl;
        this.category = category;
    }
}