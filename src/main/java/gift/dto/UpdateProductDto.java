package gift.dto;

import gift.domain.Category;
import gift.domain.Option;
import gift.domain.Product;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.stream.Collectors;

public class UpdateProductDto {
    @NotBlank
    @Size(min = 1, max = 15)
    @Pattern(regexp = "^[a-zA-Z0-9\\s()\\[\\]+\\-&/\\_]*$", message = "허용된 특수 문자는 (, ), [, ], +, -, &, /, _ 입니다.")
    @Pattern(regexp = "^(?!.*카카오).*$", message = "\"카카오\"가 포함된 상품명은 사용할 수 없습니다.")
    String name;
    @Min(0)
    Integer price;
    @NotBlank
    String imageUrl;
    @NotBlank
    Category category;

    List<Option> options;

    public String getName() {
        return this.name;
    }

    public void updateProduct(Product product) {
        List<Option> originOptions = product.getOption();
        List<Option> optionList = options.stream()
                .map(optionDto -> {
                    Option originOption = originOptions.stream()
                            .filter(o -> o.getId().equals(optionDto.getId()))
                            .findFirst()
                            .orElse(null);
                    if (originOption != null) {
                        originOption.setName(optionDto.getName());
                        return originOption;
                    } else{
                        return new Option(optionDto.getName(), product);
                    }
                })
                .collect(Collectors.toList());
        product.setOptions(optionList);
        product.update(product);
    }
}
