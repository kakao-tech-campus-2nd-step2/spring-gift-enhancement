package gift.model.categories;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Category {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
    @Column
    private String imgUrl;

    public Category() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public Category(Long id, String name, String imgUrl) {
        this.id = id;
        this.name = name;
        this.imgUrl = imgUrl;
    }

    public CategoryDTO toDTO(){
        return new CategoryDTO(id,name,imgUrl);
    }
}
