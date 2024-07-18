package gift.administrator.product;

import gift.administrator.category.Category;
import gift.users.wishlist.WishList;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Product {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @Column(nullable = false)
    private int price;
    @Column(nullable = false, unique = true, length = 15)
    private String name;
    @Column(nullable = false)
    private String imageUrl;
    @OneToMany(mappedBy = "product", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<WishList> wishes = new ArrayList<>();
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    public Product() {
    }

    public Product(String name, int price, String imageUrl, Category category) {
        this.price = price;
        this.name = name;
        this.imageUrl = imageUrl;
        this.category = category;
    }

    public Product(long id, String name, int price, String imageUrl, Category category) {
        this.id = id;
        this.price = price;
        this.name = name;
        this.imageUrl = imageUrl;
        this.category = category;
    }

    public void update(String name, int price, String imageUrl, Category category) {
        this.price = price;
        this.name = name;
        this.imageUrl = imageUrl;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public int getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public List<WishList> getWishes() {
        return wishes;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void addWishList(WishList wishList) {
        this.wishes.add(wishList);
        wishList.setProduct(this);
    }

    public void removeWishList(WishList wishList) {
        wishes.remove(wishList);
        wishList.setProduct(null);
    }
}
