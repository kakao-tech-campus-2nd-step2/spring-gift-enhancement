package gift.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

public record CategoryDTO(
        Long productId,

        @NotBlank(message = "카테고리 이름을 입력해주세요.")
        @Length(min = 1, max = 15, message = "카테고리명 길이는 1~15자만 가능합니다.")
        String categoryName,

        @Pattern(regexp = "^#[0-9a-fA-F]{6}$")
        String color,

        @URL
        String categoryImageUrl,

        @NotNull
        String description
) { }
