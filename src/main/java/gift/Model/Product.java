package gift.Model;

import jakarta.persistence.*;

import java.util.regex.Pattern;

@Entity
public class Product {

    private static final Pattern NAME_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9ㄱ-ㅎㅏ-ㅣ가-힣 ()\\[\\]+\\-\\&/_]*$"
    );

    private static final Pattern NAME_EXCLUDE_PATTERN = Pattern.compile(
            "^(?!.*카카오).*$"
    );

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @Column(nullable = false,length = 15)
    private String name;
    @Column(nullable = false)
    private int price;
    @Column(nullable = false)
    private String imageUrl;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Category category;

    protected Product() {}

    public Product(String name, int price, String imageUrl, Category category) {
        validateName(name);
        validatePrice(price);
        validateImageUrl(imageUrl);
        validateCategory(category);

        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.category = category;
    }

    public void validateName(String name) {
        if (name.length() > 15)
            throw new IllegalArgumentException("상품 이름의 길이는 공백포함 최대 15자 입니다");


        if (name.isBlank() || name == null)
            throw new IllegalArgumentException("상품 이름은 필수입니다");

        if (!NAME_PATTERN.matcher(name).matches())
            throw new IllegalArgumentException("상품 이름에는 허용된 특수 문자만 포함될 수 있습니다: (), [], +, -, &, /, _");

        if (!NAME_EXCLUDE_PATTERN.matcher(name).matches())
            throw new IllegalArgumentException("상품 이름에 '카카오'가 포함된 문구는 담당 MD와 협의가 필요합니다.");
    }

    public void validatePrice(int price){
        if (price < 0 )
            throw new IllegalArgumentException("가격은 0원 이상이여야 합니다");
    }

    public void validateImageUrl(String imageUrl){
        if (imageUrl == null || imageUrl.isBlank())
            throw new IllegalArgumentException("imageUrl를 입력해주세요");
    }

    public void validateCategory(Category category){
        if (category == null)
            throw  new IllegalArgumentException("상품에 카테고리 지정은 필수입니다");
    }

    public Long getId() {
        return id;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}