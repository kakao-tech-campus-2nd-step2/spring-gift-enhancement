package gift.domain.dto.request;

import gift.domain.annotation.RestrictedSpecialChars;
import gift.domain.entity.Product;
import gift.domain.service.CategoryService;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.List;

public record ProductAddRequest(
    @NotNull
    @Size(min = 1, max = 15, message = "공백을 포함하여 최대 15자까지 입력할 수 있습니다.")
    @Pattern(regexp = "^(?!.*카카오).*$", message = "'카카오'가 포함된 문구는 담당 MD와 협의한 경우에만 사용할 수 있습니다.")
    @RestrictedSpecialChars
    String name,
    @NotNull
    Integer price,
    @NotNull
    String imageUrl,
    @NotNull
    Long categoryId,
    @NotNull
    List<OptionAddRequest> options
    ) {

    public static ProductAddRequest of(Product product) {
        return new ProductAddRequest(product.getName(), product.getPrice(), product.getImageUrl(), product.getCategory().getId(), OptionAddRequest.of(product.getOptions()));
    }

    public Product toEntity(CategoryService categoryService) {
        return new Product(name, price, imageUrl, categoryService.findById(categoryId));
    }
}
