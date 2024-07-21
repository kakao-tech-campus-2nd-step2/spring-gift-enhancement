package gift.Model;


import jakarta.persistence.*;

@Entity
public class Category {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false)
    private String color;
    @Column(nullable = false)
    private String imageUrl;
    private String description;


    protected Category (){}

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

    public void update(String name, String color, String imageUrl, String description){
        this.name = name;
        this.color = color;
        this. imageUrl = imageUrl;
        this.description = description;
    }
}
