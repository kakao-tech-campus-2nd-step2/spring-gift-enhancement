package gift.main.entity;

import jakarta.persistence.*;

@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false,unique = true)
    private int uniqueNumber;

    @Column(nullable = false,unique = true)
    private String name;


    public long getId() {
        return id;
    }

    public int getUniqueNumber() {
        return uniqueNumber;
    }

    public String getName() {
        return name;
    }
}
