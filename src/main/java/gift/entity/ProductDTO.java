package gift.entity;

import gift.validation.constraint.NameConstraint;
import jakarta.validation.constraints.NotNull;

public class ProductDTO {

    @NameConstraint
    @NotNull
    private String name;
    @NotNull
    private Integer price;
    @NotNull
    private String imageurl;
    @NotNull
    private Long categoryid;

    public ProductDTO(String name, Integer price, String imageurl, Long categoryid) {
        this.name = name;
        this.price = price;
        this.imageurl = imageurl;
        this.categoryid = categoryid;
    }

    public ProductDTO() {
    }

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

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public Long getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(Long categoryid) {
        this.categoryid = categoryid;
    }
}
