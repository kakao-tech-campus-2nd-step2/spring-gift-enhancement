package gift.category.model;

import jakarta.persistence.*;

@Entity
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String color;

    @Column(name = "img_url")
    private String imgUrl;

//    // JPA에서 필요로 하는 기본 생성자
//    protected Category() {
//    }

    public Category(String name) {
        this.name = name;
        this.color = color;
        this.imgUrl = imgUrl;
    }

    public Category() {

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

    public String getImgUrl() {
        return imgUrl;
    }
}