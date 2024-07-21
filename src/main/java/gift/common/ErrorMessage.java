package gift.common;

public class ErrorMessage {
    private ErrorMessage(){}

    public static final String emailAlreadyExists = "이미 가입한 이메일입니다.";
    public static final String emailNotExists = "존재하지 않는 이메일입니다.";
    public static final String passwordInvalid = "잘못된 비밀번호입니다.";
    public static final String tokenInvalid = "유효하지 않은 토큰입니다.";
    public static final String headerInvalid = "유효하지 않은 헤더입니다.";
    public static final String categoryNotExists = "카테고리가 존재하지 않습니다.";
    public static final String productNotExists = "상품이 존재하지 않습니다.";
    public static final String optionNotExists = "옵션이 존재하지 않습니다.";
    public static final String quantityInvalid = "감소량이 현재 수량보다 많습니다.";
}
