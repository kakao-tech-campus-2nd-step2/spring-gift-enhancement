package gift.model.user;

import gift.model.wishList.WishItem;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.annotations.BatchSize;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Column
    private String email;
    @NotNull
    @Column
    private String password;
    @BatchSize(size = 100)
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<WishItem> wishItemList = new ArrayList<>();


    protected User() {
    }

    public User(Long id, String email, String password) {
        this.id = id;
        this.password = password;
        this.email = email;
    }

    public User(String email, String password) {
        this(null, email, password);
    }

    public Long getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public UserDTO toDTO() {
        return new UserDTO(id, password, email);
    }

    public List<WishItem> getWishItemList() {
        return wishItemList;
    }
}