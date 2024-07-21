package gift.user.domain;

import gift.product.domain.WishList;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "users")
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;

    private String name;

    private String email;
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WishList> wishLists = new ArrayList<>();

    public User() {
    }

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<WishList> getWishLists() {
        return wishLists;
    }

    public Role getRole() {
        return role;
    }

    public void setWishLists(List<WishList> wishLists) {
        this.wishLists = wishLists;
    }
}
