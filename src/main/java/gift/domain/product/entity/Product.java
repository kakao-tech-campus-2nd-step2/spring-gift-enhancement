package gift.domain.product.entity;

import gift.exception.DuplicateOptionNameException;
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
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private String imageUrl;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Option> options = new ArrayList<>();

    protected Product() {

    }

    public Product(Long id, Category category, String name, int price, String imageUrl) {
        this.category = category;
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public Long getId() {
        return id;
    }

    public Category getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void updateInfo(Category category, String name, int price, String imageUrl) {
        this.category = category;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void removeOptions() {
        options.clear();
    }

    public void addOption(Option option) {
        validateOption(option);
        options.add(option);
        option.setProduct(this);
    }

    private void validateOption(Option option) {
        options.stream()
            .filter(existingOption -> existingOption.getName().equals(option.getName()))
            .findFirst()
            .ifPresent(sameNameOption -> {
                throw new DuplicateOptionNameException("error.duplicate.option.name");
            });
    }
}
