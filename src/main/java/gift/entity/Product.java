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

    public Product(String name, int price, String imgUrl) {
        this.id = null;
        this.name = name;
        this.price = price;
        this.imgUrl = imgUrl;
    }

    public Product(Integer price, String name, String imgUrl) {
        this.price = price;
        this.name = name;
        this.imgUrl = imgUrl;
    }

    public Product(Long id, String name, int price, String imgUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imgUrl = imgUrl;
    }

    public Product() {
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
}