package gift.dto;

import gift.entity.Option;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class OptionDto {
    
    private Long id;

    @Size(max = 50, message = "Option name is too long!")
    @Pattern(regexp = "^[a-zA-Z0-9 ()\\[\\]+\\-\\&\\/\\_가-힣]*$", message = "Option name has invalid character")
    private String name;

    @Size(min = 1, max = 99999999, message = "option length is invalid")
    private int quantity;

    public OptionDto(Long id, String name, int quantity) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getName() {
        return name;
    }

    public static OptionDto fromEntity(Option option){
        return new OptionDto(option.getId(), option.getName(), option.getQuantity());
    }
}
