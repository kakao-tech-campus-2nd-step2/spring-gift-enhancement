package gift.model;

import static gift.util.Constants.CATEGORY_DESCRIPTION_SIZE_LIMIT;
import static gift.util.Constants.INVALID_COLOR;
import static gift.util.Constants.REQUIRED_FIELD_MISSING;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @NotNull(message = REQUIRED_FIELD_MISSING)
    private String name;

    @Column(nullable = false)
    @Pattern(regexp = "^#[0-9A-Fa-f]{6}$", message = INVALID_COLOR)
    @NotNull(message = REQUIRED_FIELD_MISSING)
    private String color;

    @Column(name = "image_url", nullable = false)
    @NotNull(message = REQUIRED_FIELD_MISSING)
    private String imageUrl;

    @Size(max = 255, message = CATEGORY_DESCRIPTION_SIZE_LIMIT)
    private String description;

    protected Category() {
    }

    public Category(String name, String color, String imageUrl, String description) {
        this.name = name;
        this.color = color;
        this.imageUrl = imageUrl;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getDescription() {
        return description;
    }
}
