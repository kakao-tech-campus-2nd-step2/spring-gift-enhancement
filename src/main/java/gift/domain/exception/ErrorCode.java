package gift.domain.exception;

public enum ErrorCode {

    //Bad Requests
    OPTION_QUANTITY_OUT_OF_RANGE("EBR_OP001"),
    OPTION_UPDATE_ACTION_INVALID("EBR_OP002"),
    PRODUCT_OPTIONS_EMPTY("EBR_OP003"),
    FIELD_VALIDATION_FAIL("EBR_VF001"),

    //Conflicts
    CATEGORY_ALREADY_EXISTS("ECF_CAT001"),
    CATEGORY_HAS_PRODUCTS("ECF_CAT002"),
    MEMBER_ALREADY_EXISTS("ECF_MB001"),
    OPTION_ALREADY_EXISTS_IN_PRODUCT("ECF_OP001"),
    PRODUCT_ALREADY_EXISTS("ECF_PD001"),

    //Forbidden
    MEMBER_INCORRECT_LOGIN_INFO("EFD_MB001"),
    MEMBER_NOT_ADMIN("EFD_MB002"),
    TOKEN_EXPIRED("EFD_MB003"),
    TOKEN_STRING_INVALID("EFD_MB004"),

    //Not Found
    CATEGORY_NOT_FOUND("ENF_CAT001"),
    MEMBER_NOT_FOUND("ENF_MB001"),
    OPTION_NOT_FOUND("ENF_OP001"),
    OPTION_NOT_INCLUDED_IN_PRODUCT_OPTIONS("ENF_OP002"),
    PRODUCT_NOT_FOUND("ENF_PD001"),
    PRODUCT_NOT_INCLUDED_IN_WISHLIST("ENF_WS001"),

    //Unauthorized
    TOKEN_NOT_FOUND("EUA_MB001"),
    TOKEN_UNEXPECTED_ERROR("EUA_MB002");

    private final String code;

    ErrorCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

}
