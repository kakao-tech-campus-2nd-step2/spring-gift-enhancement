package gift.main.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.hibernate.validator.constraints.Length;

import java.util.Objects;

@Entity
public class Option {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 20)
    private String optionName;

    @Min(1)
    @Max(100000000)
    private int num;

    @ManyToOne
    @JoinColumn(name = "option_list_id")
    private OptionList optionList;

    public Option() {

    }

    public Option(String optionName, int num, OptionList optionList) {
        this.optionName = optionName;
        this.num = num;
        this.optionList = optionList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Option option = (Option) o;
        return id == option.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
