package gift.administrator.product;

import gift.administrator.category.Category;
import gift.administrator.option.Option;
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
    @OneToMany(mappedBy = "product")
    private List<WishList> wishes = new ArrayList<>();
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
    @OneToMany(mappedBy = "product")
    private List<Option> options = new ArrayList<>();

    public Product() {
    }

    public Product(String name, int price, String imageUrl, Category category, List<Option> options) {
        this.price = price;
        this.name = name;
        this.imageUrl = imageUrl;
        this.category = category;
        this.options = options;
    }

    public Product(Long id, String name, int price, String imageUrl, Category category, List<Option> options) {
        this.id = id;
        this.price = price;
        this.name = name;
        this.imageUrl = imageUrl;
        this.category = category;
        this.options = options;
    }

    public void update(String name, int price, String imageUrl, Category category, List<Option> options) {
        this.price = price;
        this.name = name;
        this.imageUrl = imageUrl;
        this.category = category;
        this.options = options;
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

    public void setOption(List<Option> options){
        this.options = options;
    }

    public void addWishList(WishList wishList) {
        wishes.add(wishList);
        wishList.setProduct(this);
    }

    public void removeWishList(WishList wishList) {
        wishes.remove(wishList);
        wishList.setProduct(null);
    }

    public void addOption(Option option) {
        options.add(option);
        option.setProduct(this);
    }

    public void removeOption(Option option) {
        options.remove(option);
        option.setProduct(null);
    }

    public List<Option> getOptions(){
        return options;
    }
}
