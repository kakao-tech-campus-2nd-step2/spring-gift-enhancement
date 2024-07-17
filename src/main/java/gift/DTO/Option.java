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

  @Column(nullable = false,unique = true)
  private String name;

  @Column(nullable = false)
  private int quantity;

  @ManyToOne
  @JoinColumn(name = "product_id",nullable = false)
  private Product product;

  public Option(Long id, String name, int quantity){
    this.id=id;
    this.name=name;
    this.quantity=quantity;
  }

  public Option(String name, int quantity){
    this.name=name;
    this.quantity=quantity;
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public int getQuantity() {
    return quantity;
  }
}
