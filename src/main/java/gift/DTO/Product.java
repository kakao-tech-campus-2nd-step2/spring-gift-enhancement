package gift.DTO;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

  @OneToMany(mappedBy = "product")
  private final List<WishList> wishlists = new ArrayList<>();
  @OneToMany(mappedBy = "product")
  private final List<Option> options = new ArrayList<>();
  @ManyToOne(cascade = CascadeType.MERGE)
  @JoinColumn(name = "category_id", nullable = false)
  private Category category;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(nullable = false, unique = true)
  private String name;
  @Column(nullable = false)
  private int price;
  @Column(nullable = false)
  private String imageUrl;

  protected Product() {
  }

  public Product(Long id, String name, int price, String imageUrl, Category category) {
    this.id = id;
    this.name = name;
    this.price = price;
    this.imageUrl = imageUrl;
    this.category = category;
  }

  public Product(String name, int price, String imageUrl, Category category) {
    this.name = name;
    this.price = price;
    this.imageUrl = imageUrl;
    this.category = category;
  }

  public Long getId() {
    return this.id;
  }


  public String getName() {
    return this.name;
  }


  public int getPrice() {
    return this.price;
  }


  public String getImageUrl() {
    return this.imageUrl;
  }

  public Category getCategory() {
    return this.category;
  }
}
