package gift.administrator.option;

import gift.administrator.product.Product;
import gift.users.wishlist.WishList;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Option {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
    private int quantity;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    @OneToMany(mappedBy = "option", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<WishList> wishes = new ArrayList<>();

    public Option() {
    }

    public Option(String name, int quantity, Product product) {
        this.name = name;
        this.quantity = quantity;
        this.product = product;
    }

    public void update(String name, int quantity, Product product) {
        this.name = name;
        this.quantity = quantity;
        this.product = product;
    }

    public String getName(){
        return name;
    }

    public int getQuantity(){
        return quantity;
    }

    public Product getProduct(){
        return product;
    }

    public void addWishList(WishList wishList) {
        this.wishes.add(wishList);
        wishList.setOption(this);
    }

    public void addWishLists(List<WishList> wishLists) {
        for (WishList wishList : wishLists) {
            this.wishes.add(wishList);
            wishList.setOption(this);
        }
    }

    public void removeWishList(WishList wishList) {
        wishes.remove(wishList);
        wishList.setOption(null);
    }

    public void removeWishLists(List<WishList> wishLists){
        for (WishList wishList : wishLists) {
            this.wishes.remove(wishList);
            wishList.setOption(null);
        }
    }
}
