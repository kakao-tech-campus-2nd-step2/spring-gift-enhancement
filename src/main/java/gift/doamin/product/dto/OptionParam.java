package gift.doamin.product.dto;

public class OptionParam {

    private Long id;
    private String name;

    public OptionParam(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
