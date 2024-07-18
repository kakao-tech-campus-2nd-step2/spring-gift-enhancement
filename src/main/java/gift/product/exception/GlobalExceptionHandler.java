package gift.product.exception;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.sql.SQLException;

@ControllerAdvice
public class GlobalExceptionHandler {

    // 상품과 관련된 에러 메세지
    public static final String NOT_EXIST_ID = "존재하지 않는 ID에 대한 접근입니다.";
    public static final String CONTAINS_PRODUCT_NAME_KAKAO = "'카카오'가 포함된 문구는 담당 MD와 협의한 경우에만 사용할 수 있습니다.";
    public static final String PRODUCT_PRICE_NOT_POSITIVE = "상품의 가격은 1 이상의 양의 정수만 가능합니다.";
    // 토큰 인증과 관련된 에러 메세지
    public static final String INVALID_TOKEN = "유효하지 않은 토큰입니다.";
    public static final String NO_PERMISSION = "본인의 위시 리스트만 수정할 수 있습니다.";
    // 유저 정보와 관련된 에러 메세지
    public static final String DUPLICATE_EMAIL = "이미 가입된 이메일입니다.";
    public static final String INVALID_INPUT = "이메일 또는 비밀번호를 잘못 입력하였습니다.";
    // 카테고리와 관련된 에러 메세지
    public static final String DUPLICATE_CATEGORY_NAME = "이미 존재하는 카테고리 입니다.";
    public static final String USING_CATEGORY = "카테고리를 제거하려면 해당 카테고리를 참조하는 상품이 없어야 합니다.";
    // 옵션과 관련된 에러 메세지
    public static final String OVER_100MILLION = "해당 상품에 등록 가능한 옵션 수(1억개)를 초과하였습니다.";
    public static final String DUPLICATE_OPTION_NAME = "동일한 이름을 가진 옵션이 상품 내에 존재합니다.";
    public static final String LAST_OPTION = "해당 상품에 남아있는 옵션이 하나밖에 없어 옵션을 삭제할 수 없습니다.";
    public static final String LEAST_QUANTITY = "옵션의 수량은 0보다 작을 수 없습니다.";

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void handleException(Exception ex, Model model) {
        model.addAttribute("errorMessage", "An unexpected error occurred: " + ex.getMessage());
    }

    @ExceptionHandler(DataAccessException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void handleDataAccessException(DataAccessException ex, Model model) {
        model.addAttribute("errorMessage", "Database access error: " + ex.getMessage());
    }

    @ExceptionHandler(SQLException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void handleSQLException(SQLException ex, Model model) {
        model.addAttribute("errorMessage", "Database error: " + ex.getMessage());
    }

    @ExceptionHandler(DuplicateException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public void handleDuplicateIdException(DuplicateException ex, Model model) {
        model.addAttribute("errorMessage", "Duplicate error: " + ex.getMessage());
    }

    @ExceptionHandler(InstanceValueException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleInstanceValueException(InstanceValueException ex, Model model) {
        model.addAttribute("errorMessage", "Value error: " + ex.getMessage());
    }

    @ExceptionHandler(LoginFailedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleLoginFailedException(LoginFailedException ex, Model model) {
        model.addAttribute("errorMessage", "Login error: " + ex.getMessage());
    }

}
