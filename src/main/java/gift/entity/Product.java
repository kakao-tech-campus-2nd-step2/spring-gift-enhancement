package gift.entity;

import gift.constants.ErrorMessage;
import gift.dto.ProductRequest;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
public class Product extends BaseEntity {

    @Column(name = "name", length = 15, nullable = false, unique = true)
    private String name;

    @Column(name = "price", nullable = false)
    private long price;

    @Column(name = "image_url")
    private String imageUrl;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Wishlist> wishlist = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category")
    private Category category;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Option> options = new ArrayList<>();

    protected Product() {
    }

    public Product(ProductRequest productRequest, Category category) {
        this(productRequest.getName(), productRequest.getPrice(), productRequest.getImageUrl(),
            category);
    }

    public Product(String name, long price, String imageUrl, Category category) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.category = category;
    }

    @Override
    public Long getId() {
        return super.getId();
    }

    public String getName() {
        return name;
    }

    public long getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public List<Wishlist> getWishlist() {
        return wishlist;
    }

    public Category getCategory() {
        return category;
    }

    public void updateProduct(ProductRequest productRequest, Category category) {
        this.name = productRequest.getName();
        this.price = productRequest.getPrice();
        this.imageUrl = productRequest.getImageUrl();
        this.category = category;
    }

    public void addWishlist(Wishlist wishlist) {
        if (wishlist == null) {
            throw new NullPointerException(ErrorMessage.NULL_POINTER_EXCEPTION_MSG);
        }
        this.wishlist.add(wishlist);
    }

    public void addOption(Option newOption) {
        if (newOption == null) {
            throw new NullPointerException(ErrorMessage.NULL_POINTER_EXCEPTION_MSG);
        }
        options.add(newOption);
    }

    public boolean isOptionDuplicate(Option newOption) {
        return options.stream()
            .anyMatch(option ->
                option.getName().equals(newOption.getName())
            );
    }

    public Optional<Option> getOptionById(Long optionId) {
        return options.stream()
            .filter(option ->
                option.getId().equals(optionId)
            )
            .findFirst();
    }

    public int getOptionSize() {
        return options.size();
    }
}
