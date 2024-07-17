package gift.domain.category;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private long id;

    @NotNull
    @Column(unique = true)
    private String name;

    protected Category() {

    }
}
