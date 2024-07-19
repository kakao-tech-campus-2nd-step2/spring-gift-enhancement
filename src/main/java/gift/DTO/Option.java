package gift.DTO;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table
public class Option {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String name;

  @Column(nullable = false)
  private int quantity;

  @ManyToOne
  @JoinColumn(name = "product_id", nullable = false)
  private Product product;

  public Option() {
  }

  public Option(Long id, String name, int quantity, Product product) {
    this.id = id;
    this.name = name;
    this.quantity = quantity;
    this.product = product;
  }

  public Option(String name, int quantity, Product product) {
    this.name = name;
    this.quantity = quantity;
    this.product = product;
  }

  public Long getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }

  public int getQuantity() {
    return this.quantity;
  }

  public Product getProduct() {
    return this.product;
  }
}
