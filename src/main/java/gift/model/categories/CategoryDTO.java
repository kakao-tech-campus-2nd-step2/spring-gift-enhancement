package gift.model.categories;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class CategoryDTO {

    private final Long id;
    @NotBlank(message = "이름은 공백일 수 없습니다.")
    @Pattern(regexp = "[a-zA-Z0-9가-힣() +\\-&\\[\\]/_]*", message = "(),[],+,-,&,/,_ 를 제외한 특수문자는 사용이 불가합니다.")
    private final String name;
    private final String imgUrl;

    public CategoryDTO(Long id, String name, String imgUrl) {
        this.id = id;
        this.name = name;
        this.imgUrl = imgUrl;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public Category toEntity() {
        return new Category(id, name, imgUrl);
    }
}
