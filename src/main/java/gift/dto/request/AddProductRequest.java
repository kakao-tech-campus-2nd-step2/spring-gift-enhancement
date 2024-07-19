package gift.dto.request;

public class AddProductRequest {
    private ProductRequest productRequest;
    private OptionRequest optionRequest;

    public ProductRequest getProductRequest() {
        return productRequest;
    }

    public OptionRequest getOptionRequest() {
        return optionRequest;
    }
}
