package gift.main.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.hibernate.validator.constraints.Length;

import java.util.List;
import java.util.Objects;

@Entity
public class OptionList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true)
    private Long productId;

    @OneToMany(mappedBy = "optionList", fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    private List<Option> options;

    @Min(1)
    @Max(100000000)
    private int optionSize;

    public OptionList() {
    }

    public OptionList(Long productId, int optionSize) {
        this.productId = productId;
        this.optionSize = optionSize;
    }

    public Long getId() {
        return id;
    }

    public List<Option> getOptions() {
        return options;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OptionList that = (OptionList) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
