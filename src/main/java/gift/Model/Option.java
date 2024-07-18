package gift.Model;

import jakarta.persistence.*;

@Entity
public class Option {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false)
    private String name;
    private int quantity;

    private Option() {}

    public Option(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void update(String name, int quantity){
        this.name = name;
        this.quantity = quantity;
    }
}
