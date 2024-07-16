package gift.model.item;

import gift.model.categories.Category;
import gift.model.wishList.WishItem;
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
import org.hibernate.annotations.BatchSize;

@BatchSize(size = 100)
@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private Long price;
    private String imgUrl;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<WishItem> wishes = new ArrayList<>();

    public Item() {
    }

    public Item(Long id, String name, Long price, String imgUrl, Category category) {
        this.id = id;
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

    public Long getPrice() {
        return price;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public List<WishItem> getWishes() {
        return wishes;
    }

    public Category getCategory() {
        return category;
    }

    public ItemDTO toDTO() {
        return new ItemDTO(id, name, price, imgUrl, category.getId());
    }
}
