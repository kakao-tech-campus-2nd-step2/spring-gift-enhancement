package gift.model;

import jakarta.persistence.*;
import gift.repository.CategoryRepository;
import gift.exception.CustomNotFoundException;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name", nullable = false, length = 15)
  private String name;

  @Column(name = "price", nullable = false)
  private int price;

  @Column(name = "image_url", nullable = false, length = 255)
  private String imageUrl;

  @ManyToOne
  @JoinColumn(name = "category_id", nullable = false)
  private Category category;

  @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Option> options = new ArrayList<>();


  protected Product() {
  }

  public Product(String name, int price, String imageUrl, Category category) {
    this.name = name;
    this.price = price;
    this.imageUrl = imageUrl;
    this.category = category;
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public int getPrice() {
    return price;
  }

  public Category getCategory() {
    return category;
  }
  public List<Option> getOptions() {
    return options;
  }

  public void addOption(Option option) {
    if (options.stream().anyMatch(o -> o.getName().equals(option.getName()))) {
      throw new IllegalArgumentException("동일한 상품 내의 옵션 이름은 중복될 수 없습니다.");
    }
    options.add(option);
    option.setProduct(this);
  }

  public void removeOption(Option option) {
    options.remove(option);
    option.setProduct(null);
  }
  public void updateFromDto(ProductDto productDto, CategoryRepository categoryRepository) {
    this.name = productDto.getName();
    this.imageUrl = productDto.getImageUrl();
    this.price = productDto.getPrice();
    this.category = categoryRepository.findById(productDto.getCategoryId())
            .orElseThrow(() -> new CustomNotFoundException("Category not found"));
  }
}
