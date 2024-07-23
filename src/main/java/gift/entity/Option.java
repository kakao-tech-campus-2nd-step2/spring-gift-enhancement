package gift.entity;

import jakarta.persistence.*;

import java.util.regex.Pattern;

@Entity
@Table(name = "option")
public class Option {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private static final Pattern NAME_PATTERN = Pattern.compile("^[\\w\\s\\(\\)\\[\\]\\+\\-\\&\\/]+$");

    protected Option() {}

    public Option(String name, int quantity) {
        if (!NAME_PATTERN.matcher(name).matches()) {
            throw new IllegalArgumentException("옳지 않은 옵션명 입니다.");
        }
        if (quantity < 1 || quantity >= 1_000_000_000) {
            throw new IllegalArgumentException("옵션 수량은 1개 이상 1억 개 미만 가능합니다.");
        }
        this.name = name;
        this.quantity = quantity;
    }

    // 필요한 필드를 설정하는 생성자
    public Option(String name, Product product, int quantity) {
        this.name = name;
        this.product = product;
        this.quantity = quantity;
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

    public void subtractQuantity(int quantityToSubtract) {
        if (quantityToSubtract < 1) {
            throw new IllegalArgumentException("1 이상부터 뺼 수 있습니다.");
        }
        if (this.quantity < quantityToSubtract) {
            throw new IllegalArgumentException("뺄 수 있는 수량이 부족합니다.");
        }
        this.quantity -= quantityToSubtract;
    }

    public void assignToProduct(Product product) {  // Changed to public
        this.product = product;
    }

    public void removeFromProduct() {
        this.product = null;
    }
}