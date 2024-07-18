package gift.category;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Optional;

@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true, nullable = false)
    private String name;

    public String getName() {
        return name;
    }

    protected Category() {
    }

    public Category(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public void updateCategory(Category category) {
        Optional.ofNullable(category.name)
            .ifPresent(updateName -> this.name = updateName);
    }

    public void updateCategory(CategoryDTO categoryDTO) {
        updateCategory(new Category(
            -1L,
            categoryDTO.getName()
        ));
    }

}
