package gift.util;

public class Constants {

    // Category
    public static final String CATEGORY_NOT_FOUND = "카테고리를 다음의 id로 찾을 수 없습니다. id: ";
    public static final String INVALID_COLOR = "색상 코드 형식이 옳지 않습니다.";
    public static final String CATEGORY_DESCRIPTION_SIZE_LIMIT = "카테고리 설명은 공백을 포함하여 최대 255자까지 입력할 수 있습니다.";

    // Product
    public static final String PRODUCT_NOT_FOUND = "상품을 다음의 id로 찾을 수 없습니다. id: ";
    public static final String INVALID_PRICE = "가격은 0 이상으로 설정되어야 합니다.";
    public static final String REQUIRED_FIELD_MISSING = "필수 입력 사항이 누락되었습니다.";
    public static final String PRODUCT_NAME_SIZE_LIMIT = "상품 이름은 공백을 포함하여 최대 15자까지 입력할 수 있습니다.";
    public static final String PRODUCT_NAME_INVALID_CHARACTERS = "상품 이름에는 다음 특수 문자의 사용만 허용됩니다: ( ), [ ], +, -, &, /, _";
    public static final String PRODUCT_NAME_REQUIRES_APPROVAL = "\"카카오\"가 포함된 문구는 담당 MD와 협의한 경우에만 사용할 수 있습니다.";

    // Member
    public static final String INVALID_CREDENTIALS = "이메일 또는 비밀번호가 잘못되었습니다.";
    public static final String EMAIL_ALREADY_USED = "이미 사용 중인 이메일입니다.";
    public static final String ID_NOT_FOUND = "존재하지 않는 ID입니다.";
    public static final String INVALID_AUTHORIZATION_HEADER = "유효하지 않은 Authorization 헤더입니다.";
    public static final String INVALID_EMAIL = "유효한 이메일 주소를 입력해야 합니다.";
    public static final String INVALID_PASSWORD = "비밀번호는 최소 4자리 이상이어야 합니다.";

    // Wish
    public static final String WISH_NOT_FOUND = "위시를 다음의 id로 찾을 수 없습니다. id: ";
    public static final String WISH_ALREADY_EXISTS = "이미 위시 리스트에 추가된 상품입니다.";
    public static final String PERMISSION_DENIED = "삭제 권한이 없습니다.";
}
