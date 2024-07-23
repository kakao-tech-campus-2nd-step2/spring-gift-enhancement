package gift.domain.product;

import gift.domain.category.Category;
import gift.domain.option.Option;
import gift.exception.CustomException;
import gift.exception.ErrorCode;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    private String name;
    @NotNull
    private int price;
    @NotNull
    @Column(length = 15)
    private String imgUrl;

    @JoinColumn(name = "category_id")
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE)
    private List<Option> options = new ArrayList<>();

    protected Product() {
    }

    public Product(String name, int price, String imgUrl, Category category) {
        checkName(name);
        this.name = name;
        this.price = price;
        this.imgUrl = imgUrl;
        this.category = category;
    }

    public void update(String name, int price, String imgUrl, Category category) {
        checkName(name);
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

    public int getPrice() {
        return price;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public Category getCategory() {
        return category;
    }

    public List<Option> getOptions() {
        return options;
    }

    private void checkName(String name) {
        if (name.contains("카카오")) {
            throw new IllegalArgumentException("카카오가 포함된 이름은 담당 MD와 협의가 필요합니다.");
        }
    }

    public void addOption(Option option) {
        if (isAlreadyExistOption(option)) {
            throw new CustomException(ErrorCode.ALREADY_EXIST_OPTION, option.getName());
        }
        this.options.add(option);
    }

    private boolean isAlreadyExistOption(Option option) {
        return this.options.stream()
                .anyMatch(o -> o.hasSameName(option));
    }
}
