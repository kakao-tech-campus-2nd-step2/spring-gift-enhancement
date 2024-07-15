package gift.domain;

import gift.response.CategoryResponse;
import jakarta.persistence.*;

@Entity
public class Category {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String color;

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    private String description;

    public CategoryResponse toDto() {
        return new CategoryResponse(this.id, this.name, this.color, this.imageUrl, this.description);
    }

    protected Category() {
    }

}
