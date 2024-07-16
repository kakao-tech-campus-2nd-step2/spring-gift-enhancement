package gift.category;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.checkerframework.common.aliasing.qual.Unique;

@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Unique
    private String name;

    public String getName() {
        return name;
    }

    public Category() {
    }

    public Category(long id, String name) {
        this.id = id;
        this.name = name;
    }
}
