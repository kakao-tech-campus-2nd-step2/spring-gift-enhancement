package gift.dto.request;


import jakarta.validation.constraints.NotBlank;

public class CategoryRequest {

    @NotBlank(message = "카테고리명은 필수 항목입니다.")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
