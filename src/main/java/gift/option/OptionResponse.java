package gift.option;

public class OptionResponse {

    private Long id;
    private String name;
    private Integer quantity;

    private OptionResponse(Long id, String name, Integer quantity) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
    }

    public static OptionResponse from(Option option) {
        return new OptionResponse(option.getId(), option.getName(), option.getQuantity());
    }
}
