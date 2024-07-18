package gift.wishList;

import gift.option.Option;
import gift.product.Product;
import gift.user.User;
import jakarta.persistence.*;

@Entity
@Table(name = "WISHLISTS")
public class WishList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_id")
    private Option option;
    @Column(name = "count")
    private long count;

    public WishList(long count) {
        this.count = count;
    }


    public WishList() {

    }

    public WishList(long id, User user, Option option, long count) {
        this.id = id;
        this.user = user;
        this.option = option;
        this.count = count;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Option getOption() {
        return option;
    }

    public void setOption(Option option) {
        this.option = option;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
