package gift.model.category;

import gift.model.gift.Gift;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private Long id;

    @Column(unique = true)
    @NotNull
    private String name;

    @NotNull
    private String color;

    @NotNull
    private String imageUrl;

    @NotNull
    private String description;

    @OneToMany(mappedBy = "category")
    protected List<Gift> gifts = new ArrayList<>();

    public Category() {
    }

    public Category(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
