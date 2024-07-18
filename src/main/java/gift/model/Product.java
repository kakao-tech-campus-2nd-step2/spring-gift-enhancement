package gift.model;

import gift.common.exception.DuplicateDataException;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(indexes = @Index(name = "idx_product_created_at", columnList = "created_at"))
public class Product extends BasicEntity{
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false, length = 1000)
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private final List<Option> options = new ArrayList<>();

    protected Product() {}

    public Product(String name, int price, String imageUrl, Category category, Option option) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.category = category;
        addOption(option);
    }

    public void updateProduct(String name, int price, String imageUrl, Category category) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.category = category;
    }

    public void addOption(Option entity) {
        if (entity == null) {
            return;
        }
        checkDuplicateName(entity.getName());
        options.add(entity);
    }

    public void checkDuplicateName(String theirName) {
        for (Option option : options) {
            if(option.isSameName(theirName)) {
                throw new DuplicateDataException("Option with name " + theirName + " already exists");
            }
        }
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

    public Category getCategory() {
        return category;
    }

    public List<Option> getOptions() {
        return options;
    }
}
