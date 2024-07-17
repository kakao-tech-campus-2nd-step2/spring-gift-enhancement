package gift.controller;

import gift.exception.ErrorCode;
import gift.exception.customException.CustomArgumentNotValidException;
import gift.exception.customException.CustomDuplicateException;
import gift.exception.customException.PassWordMissMatchException;
import gift.model.user.UserForm;
import gift.service.JwtProvider;
import gift.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final UserService userService;
    private final JwtProvider jwtProvider;

    public AuthController(UserService userService, JwtProvider jwtProvider) {
        this.userService = userService;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<?> handleLoginRequest(@Valid @RequestBody UserForm userForm,
        BindingResult result)
        throws MethodArgumentNotValidException {
        if (result.hasErrors()) {
            throw new CustomArgumentNotValidException(result, ErrorCode.BAD_REQUEST);
        }
        if (!userService.existsEmail(userForm.getEmail())) {
            result.rejectValue("email", "", ErrorCode.EMAIL_NOT_FOUND.getMessage());
            throw new CustomArgumentNotValidException(result, ErrorCode.EMAIL_NOT_FOUND);
        }
        if (!userService.isPasswordMatch(userForm)) {
            result.rejectValue("password", "", ErrorCode.PASSWORD_MISMATCH.getMessage());
            throw new PassWordMissMatchException(result, ErrorCode.PASSWORD_MISMATCH);
        }
        return ResponseEntity.ok(
            jwtProvider.generateToken(userService.findByEmail(userForm.getEmail())));
    }

    @PostMapping("/register")
    public ResponseEntity<?> handleSignUpRequest(@Valid @RequestBody UserForm userForm,
        BindingResult result) throws MethodArgumentNotValidException {
        if (result.hasErrors()) {
            throw new CustomArgumentNotValidException(result, ErrorCode.BAD_REQUEST);
        }
        if (userService.existsEmail(userForm.getEmail())) {
            result.rejectValue("email", "", ErrorCode.DUPLICATE_EMAIL.getMessage());
            throw new CustomDuplicateException(result, ErrorCode.DUPLICATE_EMAIL);
        }
        Long id = userService.insertUser(userForm);
        return ResponseEntity.ok(id);
    }

}