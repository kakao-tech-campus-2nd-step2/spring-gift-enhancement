package gift.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Option {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "Option", columnDefinition = "varchar(1000)")
    @Convert(converter = ListStringConverter.class)
    private List<String> option = new ArrayList<>();

    protected Option() {
        this.option = new ArrayList<>();
    }

    public Option(String option){
        this.option = List.of(option.split(","));
    }

    public Option(List<String> option){
        this.option = option;
    }

    public Option(Long id, List<String> option){
        this.id = id;
        this.option = option;
    }
    public List<String> getOptionList() {
        return option;
    }

    public long getId() {
        return id;
    }

    public Option delete(String optionName) {
        List<String> options = this.option;
        List<String> newOptions = new ArrayList<>();
        newOptions.addAll(options);
        newOptions.remove(optionName);
        this.option = newOptions;
        return this;
    }

    public Option add(String optionName){
        List<String> options = this.option;
        List<String> newOptions = new ArrayList<>();
        newOptions.addAll(options);
        newOptions.add(optionName);
        this.option = newOptions;
        return this;
    }

    public Option update(String oldName, String newName){
        List<String> options = this.option;
        List<String> newOptions = new ArrayList<>();
        newOptions.addAll(options);
        newOptions.set(option.indexOf(oldName), newName);
        this.option = newOptions;
        return this;
    }

    @Override
    public String toString() {
        return "Option{" +
                "id=" + id +
                ", option=" + option +
                '}';
    }
}
