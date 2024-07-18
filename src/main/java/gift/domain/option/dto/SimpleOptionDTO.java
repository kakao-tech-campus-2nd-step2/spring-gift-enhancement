package gift.domain.option.dto;


import gift.domain.option.Option;

public class SimpleOptionDTO { // 상품 정보 제외

    private Long id;
    private String name;
    private Long quantity;

    public SimpleOptionDTO(Option option) {
        this.id = option.getId();
        this.name = option.getName();
        this.quantity = option.getQuantity();
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
