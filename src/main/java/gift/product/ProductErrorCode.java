package gift.product;

public enum ProductErrorCode {
    HAS_KAKAO_WORD("\"카카오\"가 포함된 문구는 담당자와 협의 후 사용할 수 있습니다."),
    NOT_FOUND("Product 가 발견되지 않았습니다.");

    private final String message;

    ProductErrorCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
