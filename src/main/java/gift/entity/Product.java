package gift.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int price;
    private String imageurl;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProductWishlist> productWishlist = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProductOption> productOption = new ArrayList<>();

    private Long categoryid;

    public Product() {
    }

    public Product(ProductDTO product) {
        this.name = product.getName();
        this.price = product.getPrice();
        this.imageurl = product.getImageurl();
        this.categoryid = product.getCategoryid();
    }

    public Product(String name, int price, String imageurl, Long categoryid) {
        this.name = name;
        this.price = price;
        this.imageurl = imageurl;
        this.categoryid = categoryid;
    }

    public void setProductWithCategory(ProductDTO product) {
        this.name = product.getName();
        this.price = product.getPrice();
        this.imageurl = product.getImageurl();
        this.categoryid = categoryid;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getImageurl() {
        return imageurl;
    }

    public Long getCategoryid() {
        return categoryid;
    }

    public Long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public void setCategoryid(Long categoryid) {
        this.categoryid = categoryid;
    }
}
