package gift.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import gift.dto.OptionRequestDTO;
import gift.dto.ProductRequestDTO;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, name = "id")
    private Long id;

    @Column(nullable = false, name = "name")
    private String name;

    @Column(nullable = false, name = "price")
    private int price;

    @Column(nullable = false, name = "image_url")
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "product", orphanRemoval = true)
    private List<Wish> wishes = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "product", orphanRemoval = true)
    private List<Option> options = new ArrayList<>();

    public Product() {}

    public Product(String name, int price, String imageUrl, Category category) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<Wish> getWishes() {
        return wishes;
    }

    public void setWishes(List<Wish> wishes) {
        this.wishes = wishes;
    }

    public void updateProduct(ProductRequestDTO productRequestDTO){
        this.name=productRequestDTO.name();
        this.price=productRequestDTO.price();
        this.imageUrl=productRequestDTO.imageUrl();

    }

    public void addOption(OptionRequestDTO optionRequestDTO){
        Option option = new Option(
                optionRequestDTO.name(),
                optionRequestDTO.quantity(),
                this
        );
        this.options.add(option);
    }

}