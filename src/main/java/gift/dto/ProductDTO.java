package gift.dto;

import gift.domain.Category;
import gift.domain.CategoryName;
import gift.domain.Option;
import gift.domain.Product;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class ProductDTO {

    @NotBlank(message = "상품 이름은 필수 입력 항목입니다.")
    @Size(max = 15, message = "상품 이름은 최대 15자까지 입력할 수 있습니다.")
    @Pattern(regexp = "^[\\p{L}0-9 ()\\[\\]+\\-&/_]+$", message = "상품 이름에 사용 가능한 특수문자는 ( ), [ ], +, -, &, /, _ 입니다")
    @Pattern(regexp = "^(?!.*(?i)(kakao|카카오).*$).*$", message = "상품 이름에 '카카오'를 사용할 수 없습니다.")
    private String name;

    @NotNull(message = "가격은 필수 입력 항목입니다.")
    @DecimalMin(value = "0.0", inclusive = false, message = "가격은 0보다 커야 합니다.")
    private BigDecimal price;

    private String imageUrl;

    private String description;

    @NotNull(message = "카테고리는 필수 입력 항목입니다.")
    private CategoryName categoryName;

    private List<OptionDTO> options;  // 옵션 리스트 추가

    public ProductDTO() {}

    private ProductDTO(ProductDTOBuilder builder) {
        this.name = builder.name;
        this.price = builder.price;
        this.imageUrl = builder.imageUrl;
        this.description = builder.description;
        this.categoryName = builder.categoryName;
        this.options = builder.options;  // 옵션 추가
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public CategoryName getCategoryName() {
        return categoryName;
    }

    public List<OptionDTO> getOptions() {
        return options;  // 옵션 리스트 반환 메소드 추가
    }

    public static class ProductDTOBuilder {
        private String name;
        private BigDecimal price;
        private String imageUrl;
        private String description;
        private CategoryName categoryName;
        private List<OptionDTO> options;  // 옵션 리스트 추가

        public ProductDTOBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ProductDTOBuilder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public ProductDTOBuilder imageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public ProductDTOBuilder description(String description) {
            this.description = description;
            return this;
        }

        public ProductDTOBuilder categoryName(CategoryName categoryName) {
            this.categoryName = categoryName;
            return this;
        }

        public ProductDTOBuilder options(List<OptionDTO> options) {  // 옵션 리스트 추가
            this.options = options;
            return this;
        }

        public ProductDTO build() {
            return new ProductDTO(this);
        }
    }

    public Product toEntity(Category category) {
        List<Option> optionEntities = this.options.stream()  // 옵션 리스트 엔티티 변환
            .map(optionDTO -> optionDTO.toEntity())
            .collect(Collectors.toList());

        return new Product.ProductBuilder()
            .name(this.name)
            .price(this.price)
            .imageUrl(this.imageUrl)
            .description(this.description)
            .category(category)
            .options(optionEntities)  // 옵션 리스트 추가
            .build();
    }
}
