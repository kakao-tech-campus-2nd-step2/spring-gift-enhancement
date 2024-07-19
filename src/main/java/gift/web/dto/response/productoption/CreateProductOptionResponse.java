package gift.web.dto.response.productoption;

public class CreateProductOptionResponse {

    private final Long id;
    private final String name;
    private final Integer stock;

    public CreateProductOptionResponse(Long id, String name, Integer stock) {
        this.id = id;
        this.name = name;
        this.stock = stock;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getStock() {
        return stock;
    }

}
