package gift.dto;

import gift.entity.Category;
import gift.entity.Product;
import java.util.List;

public class ProductDTO {

    private String name;
    private int price;
    private String imageUrl;

    private Long categoryId;

    private List<OptionDTO> option;


    public ProductDTO() {
    }


    public ProductDTO(Long id, String name, int price, String imageUrl, Long categoryId,
        List<OptionDTO> option) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.categoryId = categoryId;
        this.option = option;
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

    public Long getCategoryId() {
        return categoryId;
    }

    public List<OptionDTO> getOption() {
        return option;
    }

    public Product toEntity(Category category) {
        return new Product(name, price, imageUrl, category);
    }


}
