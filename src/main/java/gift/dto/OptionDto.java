package gift.dto;

public class OptionDto {
    private Long id;
    private String name;
    private int amount;
    private ProductDto ProductDto;

    public OptionDto(Long id, String name, int amount, gift.dto.ProductDto productDto) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        ProductDto = productDto;
    }
}
