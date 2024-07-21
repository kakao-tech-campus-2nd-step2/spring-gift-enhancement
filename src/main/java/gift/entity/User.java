package gift.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="user_tb")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique=true, nullable=false)

    private String email;
    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "user")
    List<WishList> wishlist = new ArrayList<>();

    public User() {
    }
    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public void addWishlist(WishList wishlist){
        this.wishlist.add(wishlist);
    }

    public void deleteWishlist(WishList wishList) {
        this.wishlist.remove(wishList);
    }

    public void deleteWishlist(WishList wishList) {
        this.wishlist.remove(wishList);
    }
}
