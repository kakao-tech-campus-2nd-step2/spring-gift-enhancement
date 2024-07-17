package gift.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Option {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "Option", columnDefinition = "varchar(1000)")
    @Convert(converter = ListStringConverter.class)
    private List<String> option;

    protected Option() {
    }

    public Option(String option){
        this.option = List.of(option.split(","));
    }
    public List<String> getOptionList() {
        return option;
    }

    public long getId() {
        return id;
    }
}
