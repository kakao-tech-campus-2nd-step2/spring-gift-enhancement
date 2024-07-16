package gift.model.product;

import gift.model.wish.Wish;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 15)
    @Embedded
    private  ProductName name;

    @Column(nullable = false)
    private  int price;

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    private  int amount;

    @OneToMany(mappedBy = "product" , fetch = FetchType.LAZY)
    private List<Wish> wishes;

    protected Product(){
    }

    public Product(ProductName name, int price, String imageUrl, int amount){
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.amount = amount;
    }

    public void updateProduct(Product product){
        this.name = product.getName();
        this.price = product.getPrice();
        this.imageUrl = product.getImageUrl();
        this.amount = product.getAmount();
    }

    public long getId() {
        return id;
    }

    public ProductName getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getAmount() {
        return amount;
    }

    public boolean isProductEnough(int purchaseAmount){
        if(amount > purchaseAmount){
            return true;
        }
        return false;
    }
}
