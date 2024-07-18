package gift.constant;

public enum ErrorMessage {

    /* Validation */
    VALIDATION_ERROR("입력 데이터의 유효성을 검사하던 중 문제가 발생했습니다."),

    /* Product */
    PRODUCT_NOT_FOUND("존재하지 않는 상품입니다."),

    /* Member */
    MEMBER_NOT_FOUND("존재하지 않는 회원입니다."),

    /* JWT */
    INVALID_TOKEN("유효하지 않거나 만료된 토큰입니다."),
    MISSING_TOKEN("헤더에 토큰이 존재하지 않거나 잘못된 형식입니다."),

    /* MemberService */
    DUPLICATED_EMAIL("중복된 이메일입니다."),
    LOGIN_FAILURE("이메일 또는 비밀번호가 일치하지 않습니다."),

    /* WishlistService */
    PRODUCT_ALREADY_IN_WISHLIST("이미 위시리스트에 추가된 상품입니다."),
    PRODUCT_NOT_IN_WISHLIST("이미 위시리스트에 존재하지 않는 상품입니다."),

    /* CategoryService */
    CATEGORY_NOT_FOUND("존재하지 않는 카테고리입니다."),
    CATEGORY_NAME_NOT_DUPLICATES("이미 존재하는 카테고리 이름입니다."),

    /* ProductService */
    DUPLICATE_OPTION("중복된 옵션입니다."),
    OPTION_NOT_FOUND("존재하지 않는 옵션입니다."),
    NO_OPTIONS_IN_PRODUCT("상품에는 반드시 하나 이상의 옵션이 있어야 합니다.");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

}
