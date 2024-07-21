//package gift.option.model;
//
//import jakarta.validation.constraints.Max;
//import jakarta.validation.constraints.Min;
//import jakarta.validation.constraints.NotNull;
//
//public class OptionQuantity {
//    @NotNull(message = "수량은 필수 값입니다.")
//    @Min(value = 1, message = "옵션 수량은 최소 1개 이상이어야 합니다.")
//    @Max(value = 99999999, message = "옵션 수량은 1억 개 미만이어야 합니다.")
//    private Integer value;
//
//    protected OptionQuantity() {}
//
//    public OptionQuantity(Integer value) {
//        this.value = value;
//        validate();
//    }
//
//    public Integer getValue() {
//        return value;
//    }
//
//    private void validate() {
//        if (value < 1 || value >= 100000000) {
//            throw new IllegalArgumentException("옵션 수량은 최소 1개 이상 1억 개 미만이어야 합니다.");
//        }
//    }
//}
