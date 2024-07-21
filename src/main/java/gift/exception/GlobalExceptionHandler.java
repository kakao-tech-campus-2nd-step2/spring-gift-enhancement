//package gift.exception;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@ControllerAdvice
//public class GlobalExceptionHandler {
//
//    // 유효성 검사 실패 시 호출되는 메소드
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
//        Map<String, String> errors = new HashMap<>();
//
//        // 유효성 검사 실패 필드와 메시지 추출
//        ex.getBindingResult().getFieldErrors().forEach(error ->
//                errors.put(error.getField(), error.getDefaultMessage())
//        );
//
//        // 클라이언트에 응답 반환
//        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
//    }
//
//    // 상품명에 '카카오'가 포함된 경우 호출되는 메소드
//    @ExceptionHandler(KakaoProductException.class)
//    public ResponseEntity<String> handleKakaoProductException(KakaoProductException ex) {
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
//    }
//
//    // 401 Unauthorized 예외 처리
//    @ExceptionHandler(UnauthorizedException.class)
//    public ResponseEntity<String> handleUnauthorizedException(UnauthorizedException ex) {
//        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
//    }
//
//    // 403 Forbidden 예외 처리
//    @ExceptionHandler(ForbiddenException.class)
//    public ResponseEntity<String> handleForbiddenException(ForbiddenException ex) {
//        return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
//    }
//
//    // 회원을 찾을 수 없는 경우 예외 처리
//    @ExceptionHandler(MemberNotFoundException.class)
//    public ResponseEntity<String> handleMemberNotFoundException(MemberNotFoundException ex) {
//        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
//    }
//}