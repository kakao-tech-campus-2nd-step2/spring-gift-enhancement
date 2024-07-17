package gift.product.dto;

import jakarta.validation.constraints.NotBlank;

public class CategoryDTO {

    private Long id;
    @NotBlank(message = "카테고리 이름은 필수 입력사항입니다.")
    private String name;

    public CategoryDTO() {}
    public CategoryDTO(String name) {}
    public CategoryDTO(Long id, String name) {}

    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
