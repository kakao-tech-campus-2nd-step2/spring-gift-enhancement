package gift.domain;

import jakarta.persistence.*;

import java.util.LinkedList;
import java.util.List;

@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name",nullable = false)
    private String name;

    @OneToMany(mappedBy = "category")
    private List<Menu> menus;

    public Category(Long id, String name, LinkedList<Menu> menus) {
        this.id = id;
        this.name = name;
        this.menus = menus;
    }

    public Category(Long id, String name) {
        this(id, name,new LinkedList<Menu>());
    }

    public Category() {

    }
}
