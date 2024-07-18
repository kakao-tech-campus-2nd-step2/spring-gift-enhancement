package gift.dto;

import gift.dto.response.OptionResponse;

public class OptionDto {

    private Long id;
    private String name;
    private Long quantity;

    public OptionDto(String name, Long quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public OptionDto(Long id, String name, Long quantity) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
    }

    public OptionResponse toResponseDto() {
        return new OptionResponse(this.id, this.name, this.quantity);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getQuantity() {
        return quantity;
    }

}
