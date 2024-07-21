package gift.dto.option;

import gift.model.option.Option;

public class OptionResponse {

    private Long id;

    private String name;

    private int quantity;

    public OptionResponse(Long id, String name, int quantity) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
    }

    public static OptionResponse from(Option option) {
        return new OptionResponse(option.getId(), option.getName(), option.getQuantity());
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
}