package gift.model.product;

import jakarta.persistence.*;

@Entity
@Table(name = "products")
public class Product {

    @ManyToOne
    @JoinColumn(nullable = false, name = "category_id")
    private Category category;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 15, columnDefinition = "VARCHAR(15)")
    @Embedded
    private ProductName name;

    @Column(nullable = false, columnDefinition = "integer")
    private int price;

    @Column(nullable = false , columnDefinition = "VARCHAR(255)")
    private String imageUrl;

    @Column(nullable = false, columnDefinition = "integer")
    private int amount;

    protected Product(){
    }

    public Product(Category category, ProductName name, int price, String imageUrl, int amount){
        this.category = category;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.amount = amount;
    }

    public void updateProduct(Product product){
        this.category = product.getCategory();
        this.name = product.getName();
        this.price = product.getPrice();
        this.imageUrl = product.getImageUrl();
        this.amount = product.getAmount();
    }

    public Category getCategory() {
        return category;
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
