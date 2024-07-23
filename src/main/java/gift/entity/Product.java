package gift.entity;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Entity
@Table(name = "products")
public class Product {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @NotNull
    @Size(max = 15, message = "상품 이름은 공백을 포함하여 최대 15자까지 입력할 수 있습니다.")
    private String name;
    @NotNull
    private Integer price;
    private String img;
    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE)
    private List<Wish> wishes = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "category_id")
    @NotNull
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<Option> options = new ArrayList<>();

    protected Product() {
    }

    public Product(String name, Integer price, String img, Category category) {
        this.name = name;
        this.price = price;
        this.img = img;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getImg() {
        return img;
    }

    public Category getCategory() {
        return category;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void updateProduct(String name, Integer price, String img, Category category) {
        this.name = name;
        this.price = price;
        this.img = img;
        this.category = category;
    }

    public List<Option> sortAndBringOptions() {
        options.sort(Comparator.comparing(Option::getId));
        return options;
    }

    public void removeOption(Option option) {
        this.options.remove(option);
    }

    public Integer optionAmount() {
        return this.options.size();
    }
}
