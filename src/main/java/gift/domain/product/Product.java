package gift.domain.product;

import gift.domain.BaseTimeEntity;
import gift.domain.category.Category;
import gift.domain.option.Option;
import gift.global.annotation.NotContainsValue;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Entity
public class Product extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    @NotContainsValue(value = "카카오", message = "'{value}' 가 포함된 문구는 담당 MD 와 협의 후 사용 가능합니다.")
    private String name;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    // 상품 삭제 시 옵션도 같이 삭제(옵션은 하나의 상품에 종속)
    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Option> options = new ArrayList<>();
    private int price;
    private String imageUrl;
    protected Product() {

    }

    public Product(String name, Category category, int price, String imageUrl) {
        this.name = name;
        this.category = category;

        this.price = price;
        this.imageUrl = imageUrl;
    }

    public Product(Long id, String name, Category category, int price, String imageUrl) {
        this.id = id;
        this.name = name;
        this.category = category;

        this.price = price;
        this.imageUrl = imageUrl;
    }

    public static Product createProductFromProxy(Product proxyProduct) {
        return new Product(
            proxyProduct.getId(),
            proxyProduct.getName(),
            proxyProduct.getCategory(),
            proxyProduct.getPrice(),
            proxyProduct.getImageUrl());
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Category getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return "Product{" +
               "id=" + id +
               ", name='" + name + '\'' +
               ", category=" + category +
               ", options=" + options +
               ", price=" + price +
               ", imageUrl='" + imageUrl + '\'' +
               '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Product product = (Product) o;

//        // 프록시 객체 초기화
//        Hibernate.initialize(product.getCategory());
//        Hibernate.initialize(category);

        return id == product.id &&
               price == product.price &&
               Objects.equals(name, product.name) &&
               Objects.equals(imageUrl, product.imageUrl) &&
               Objects.equals(category.getId(), product.category.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, category, price, imageUrl);
    }

    public void update(String name, Category category, int price, String imageUrl) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.imageUrl = imageUrl;
    }


    public List<Option> getOptions() {
        return options;
    }
}
