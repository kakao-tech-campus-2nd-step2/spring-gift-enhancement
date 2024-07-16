package gift.domain;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    @ManyToOne
    private Category category;

    public Menu() {
    }

    public Menu(String name, int price, String imageUrl) {
        this(null, name, price, imageUrl, null);
    }

    public Menu(Long id, String name, int price, String imageUrl, Category category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.category = category;
    }

    public Menu(Long id, MenuRequest menuRequest) {
        this.id = id;
        this.name = menuRequest.name();
        this.price = menuRequest.price();
        this.imageUrl = menuRequest.imageUrl();
        this.category = menuRequest.category();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void update(Menu menu) {
        this.id = menu.id;
        this.name = menu.name;
        this.price = menu.price;
        this.imageUrl = menu.imageUrl;
        this.category = menu.category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Menu menu = (Menu) o;
        return Objects.equals(id, menu.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
