package gift.option.dto;

public class OptionDto {

    private Long optionId;
    private String name;
    private int quantity;

    // 기본 생성자
    public OptionDto() {
    }

    // 모든 필드를 인자로 받는 생성자
    public OptionDto(Long optionId, String name, int quantity) {
        this.optionId = optionId;
        this.name = name;
        this.quantity = quantity;
    }

    public OptionDto(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public Long getOptionId() {
        return optionId;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }
}