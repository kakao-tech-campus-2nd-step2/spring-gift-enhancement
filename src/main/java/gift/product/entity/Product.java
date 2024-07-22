package gift.product.entity;

import gift.product.category.entity.Category;
import gift.product.dto.request.UpdateProductRequest;
import gift.product.option.entity.Option;
import gift.product.option.entity.Options;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PostLoad;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.util.HashSet;
import java.util.Set;
import org.springframework.util.Assert;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 15, nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false,
        foreignKey = @ForeignKey(name = "fk_products_category_id_ref_categories_id"))
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Option> optionSet = new HashSet<>();

    @Transient
    private Options options;

    protected Product() {
    }

    private Product(Builder builder) {
        this.name = builder.name;
        this.price = builder.price;
        this.imageUrl = builder.imageUrl;
        this.category = builder.category;
        this.optionSet = new HashSet<>(builder.options);
        this.options = new Options(this.optionSet);
    }

    public static Builder builder() {
        return new Builder();
    }

    public void edit(UpdateProductRequest request, Category category) {
        this.name = request.name();
        this.price = request.price();
        this.imageUrl = request.imageUrl();
        this.category = category;
    }

    public void addOption(Option option) {
        Assert.notNull(option, "Option is null");
        option.initProduct(this);
        this.options.addOption(option);
        this.optionSet.add(option);
    }

    public void removeOption(Option option) {
        options.removeOption(option);
        optionSet.remove(option);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getCategoryName() {
        return category.getName();
    }

    public Set<Option> getOptions() {
        return optionSet;
    }

    public void changeName(String name) {
        this.name = name;
    }

    @PostLoad
    private void postLoad() {
        this.options = new Options(this.optionSet);
    }

    public static class Builder {

        private String name;
        private Integer price;
        private String imageUrl;
        private Category category;
        private Set<Option> options = new HashSet<>();

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder price(Integer price) {
            this.price = price;
            return this;
        }

        public Builder imageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public Builder category(Category category) {
            this.category = category;
            return this;
        }

        public Builder options() {
            this.options = new HashSet<>();
            return this;
        }

        public Builder options(Set<Option> options) {
            this.options = options;
            return this;
        }

        public Product build() {
            return new Product(this);
        }
    }

}
