package gift.api.category;

import gift.global.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(name = "uk_category", columnNames = {"name"}))
public class Category extends BaseEntity {
    private String name;

    protected Category(){
    }

    public Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
