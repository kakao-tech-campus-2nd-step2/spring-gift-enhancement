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
    public static final String NOT_EXIST_ID = "요청한 id가 존재하지 않습니다.";
    public static final String CONTAINS_PRODUCT_NAME_KAKAO = "'카카오'가 포함된 문구는 담당 MD와 협의한 경우에만 사용할 수 있습니다.";
    public static final String PRODUCT_PRICE_NOT_POSITIVE = "상품의 가격은 1 이상의 양의 정수만 가능합니다.";
    // 토큰 인증과 관련된 에러 메세지
    public static final String INVALID_TOKEN = "유효하지 않은 토큰입니다.";
    public static final String NO_PERMISSION = "본인의 위시 리스트만 수정할 수 있습니다.";
    // 유저 정보와 관련된 에러 메세지
    public static final String DUPLICATE_EMAIL = "이미 가입된 이메일입니다.";
    public static final String INVALID_INPUT = "이메일 또는 비밀번호를 잘못 입력하였습니다.";

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
