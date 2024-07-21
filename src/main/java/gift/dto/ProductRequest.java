package gift.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;

public class ProductRequest {
    @NotEmpty
    @Size(max = 15)
    @Pattern(regexp = "^[a-zA-Z0-9()\\[\\]+\\-&/_ ]+$", message = "Invalid characters in name")
    private String name;

    @NotNull
    private Integer price;

    @NotEmpty
    private String imageUrl;

    @NotNull
    private Long categoryId;

    @NotNull
    private List<OptionRequest> options;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Long getCategoryId() {return categoryId;}

    public void setCategoryId(Long categoryId) {this.categoryId = categoryId;}

    public List<OptionRequest> getOptions() {
        return options;
    }
    public void setOptions(List<OptionRequest> options) {
        this.options = options;
    }
}
