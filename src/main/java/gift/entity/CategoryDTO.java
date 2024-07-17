package gift.entity;


public class CategoryDTO {
    private String name;
    private String color;
    private String imageurl;
    private String description;

    public CategoryDTO() {
    }

    public CategoryDTO(String name, String color, String imageurl, String description) {
        this.name = name;
        this.color = color;
        this.imageurl = imageurl;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
