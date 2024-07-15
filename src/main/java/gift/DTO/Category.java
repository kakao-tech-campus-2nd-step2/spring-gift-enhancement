package gift.DTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table
public class Category {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String color;

  @Column(nullable = false)
  private String imageUrl;

  @Column(nullable = false)
  private String description;

  public Category() {
  }

  public Category(Long id, String name, String color, String imageUrl, String description){
    this.id=id;
    this.name=name;
    this.color=color;
    this.imageUrl=imageUrl;
    this.description=description;
  }


  public Long getId(){return this.id;}


  public String getName(){return this.name;}


  public String getColor(){return this.color;}


  public String getImageUrl(){return this.getImageUrl();}


  public String getDescription(){return this.getDescription();}

}
