package gift.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class OptionDTO {
    private long id;
    private List<String> option;

    public OptionDTO(String options){
        option = List.of(options.split(","));
    }

    public OptionDTO(Long id, List<String> option){
        this.id = id;
        this.option = option;
    }

    public long getId() {
        return id;
    }

    public List<String> getOption() {
        return option;
    }
}
