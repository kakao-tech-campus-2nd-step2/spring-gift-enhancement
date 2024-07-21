package gift.product.category.entity;

import gift.exception.CustomException;
import gift.exception.ErrorCode;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.util.regex.Pattern;

@Entity
@Table(
    name = "categories",
    uniqueConstraints = @UniqueConstraint(columnNames = {"name"}, name = "uk_categories")
)
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 7, nullable = false)
    private String color;

    @Column
    private String description;

    @Column(nullable = false)
    private String imageUrl;

    protected Category() {
    }

    public Category(String name) {
        this.name = name;
    }

    public Category(String name, String color, String description, String imageUrl) {
        validateColor(color);
        this.name = name;
        this.color = color;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public Category(Long id, String name, String color, String description, String imageUrl) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void edit(String name, String color, String description, String imageUrl) {
        validateColor(color);
        this.name = name;
        this.color = color;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    private void validateColor(String color) {
        final String colorRegex = "^#+[0-9a-fA-F]{6}$";
        Pattern colorPattern = Pattern.compile(colorRegex);
        if (!colorPattern.matcher(color).matches()) {
            throw new CustomException(ErrorCode.INVALID_COLOR);
        }
    }

}
