package gift.permission.service;

import gift.global.component.TokenComponent;
import gift.global.dto.TokenDto;
import gift.permission.dto.LoginRequestDto;
import gift.permission.dto.RegistrationRequestDto;
import gift.permission.repository.PermissionRepository;
import gift.user.entity.User;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

// UserController로부터 입력을 받아서 엔터티를 사용해서 비즈니스 로직 수행
@Service
public class PermissionService {

    private final PermissionRepository permissionRepository;
    private final TokenComponent tokenComponent;

    @Autowired
    public PermissionService(PermissionRepository permissionRepository,
        TokenComponent tokenComponent) {
        this.permissionRepository = permissionRepository;
        this.tokenComponent = tokenComponent;
    }

    // 회원가입 비즈니스 로직 처리
    @Transactional
    public void register(RegistrationRequestDto registrationRequestDto) {
        try {
            User user = new User(registrationRequestDto.email(), registrationRequestDto.password(),
                registrationRequestDto.isAdmin());
            permissionRepository.save(user);
        } catch (Exception e) {
            // email이 unique하지 않은 경우. exist를 써서 쿼리를 한 번 더 날리지 않기 위해 이렇게 구성했습니다.
            throw new IllegalArgumentException("이미 가입된 이메일입니다.");
        }

    }

    // 로그인 비즈니스 로직 처리
    @Transactional
    public TokenDto userLogin(LoginRequestDto loginRequestDto) {
        String email = loginRequestDto.email();
        String password = loginRequestDto.password();

        // 이메일이 멀쩡하면 이메일로 불러오기
        Optional<User> optionalUser = permissionRepository.findByEmail(email);
        verifyExistence(optionalUser);
        User actualUser = optionalUser.get();

        // 비밀번호 검증
        verifyPassword(password, actualUser.getPassword());

        // 검증이 완료되면 토큰 발급. 어드민 권한이 있는 유저라도 userLogin으로 오면 일반 유저 권한으로 로그인
        return tokenComponent.getToken(actualUser.getUserId(), email, false);
    }

    // 어드민 로그인 비즈니스 로직
    @Transactional
    public TokenDto adminLogin(LoginRequestDto loginRequestDto) {
        String email = loginRequestDto.email();
        String password = loginRequestDto.password();

        // 이메일이 멀쩡하면 이메일로 불러오기
        Optional<User> optionalUser = permissionRepository.findByEmail(email);
        verifyExistence(optionalUser);
        User actualUser = optionalUser.get();

        // 비밀번호 검증
        verifyPassword(password, actualUser.getPassword());

        boolean isAdmin = actualUser.isAdmin();
        if (!isAdmin) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "어드민 권한이 없습니다.");
        }

        // 검증이 완료되면 토큰 발급
        return tokenComponent.getToken(actualUser.getUserId(), email, true);
    }

    // 존재하는 유저인지 검증
    private void verifyExistence(Optional<User> optionalUser) {
        if (optionalUser.isEmpty()) {
            throw new IllegalArgumentException("가입되지 않은 이메일입니다.");
        }
    }

    // 입력으로 들어온 비밀번호를 검증하는 로직
    private void verifyPassword(String inputPassword, String realPassword) {
        // 요구 사항: 비밀번호가 옳지 않으면 FORBIDDEN 반환.
        if (!inputPassword.equals(realPassword)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "비밀번호가 맞지 않습니다.");
        }
    }
}