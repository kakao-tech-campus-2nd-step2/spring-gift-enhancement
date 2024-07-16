package gift.model.product;

import jakarta.persistence.*;

@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String categoryName;

    protected Category(){
    }
    public Category(String categoryName){
        this.categoryName = categoryName;
    }

    public long getId() {
        return id;
    }

    public String getCategoryName() {
        return categoryName;
    }
}
