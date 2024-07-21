package gift.vo;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fk_product_category_id_ref_category_id"))
    Category category;

    @NotNull
    @Size(max = 15)
    private String name;

    @NotNull
    private Integer price;

    @NotNull
    private String imageUrl;

    public Product() {}

    public Product(Category category, String name, int price, String imageUrl) {
        this(null, category, name, price, imageUrl);
    }

    public Product(Long id, Category category, String name, int price, String imageUrl) {
        validateName(name);

        this.id = id;
        this.category = category;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    private static void validateName(String name) {
        if (name == null || name.length() > 15) {
            throw new IllegalArgumentException("상품명은 15자를 넘을 수 없습니다.");
        }

        if (!name.matches("^[a-zA-Z0-9가-힣 ()\\[\\]+\\-&/]*$")) {
            throw new IllegalArgumentException("상품명에 () [] + - & / 외의 특수기호는 불가합니다");
        }

        if (name.contains("카카오")) {
            throw new IllegalArgumentException("`카카오`가 포함된 문구는 담당 MD와 협의한 경우에만 사용 가능합니다");
        }
    }

    public Long getId() {
        return id;
    }

    public Category getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
