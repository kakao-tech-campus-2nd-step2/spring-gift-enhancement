package gift.Model;

import jakarta.persistence.*;

import java.util.regex.Pattern;

@Entity
public class Option {

    private static final Pattern NAME_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9ㄱ-ㅎㅏ-ㅣ가-힣 ()\\[\\]+\\-\\&/_]*$"
    );

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false)
    private String name;

    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Product product;

    private Option() {}

    public Option(String name, int quantity, Product product) {
        validateName(name);
        validateQuantity(quantity);
        validateProduct(product);

        this.name = name;
        this.quantity = quantity;
        this.product = product;
    }

    public void validateName(String name) {
        if (name.length() > 50)
            throw new IllegalArgumentException("옵션 이름의 길이는 공백포함 최대 50자 입니다");

        if (name.isBlank() || name == null)
            throw new IllegalArgumentException("옵션 이름은 필수입니다");

        if (!NAME_PATTERN.matcher(name).matches())
            throw new IllegalArgumentException("옵션 이름에는 허용된 특수 문자만 포함될 수 있습니다: (), [], +, -, &, /, _");

    }

    public void validateQuantity(int quantity) {
        if (quantity <= 0)
            throw new IllegalArgumentException("옵션 수량은 최소 1개 이상이여야 합니다");
        if (quantity > 9999_9999)
            throw new IllegalArgumentException("옵션 수량은 최대 1억개 미만이여야 합니다");
    }

        public void validateProduct(Product product) {
        if (product == null)
            throw new IllegalArgumentException("옵션에 product 지정은 필수입니다");
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

    public Product getProduct() {
        return product;
    }

    public void update(String name, int quantity){
        this.name = name;
        this.quantity = quantity;
    }

    public void subtract(int quantity){
        this.quantity -= quantity;
    }

    public boolean hasSameName(String name){
        return this.name.equals(name);
    }

    public boolean isBelongToProduct(Long productId){
        return this.product.getId().equals(productId);
    }
}
