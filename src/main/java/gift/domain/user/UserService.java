package gift.domain.user;

import gift.domain.user.dto.UserDTO;
import gift.global.exception.BusinessException;
import gift.global.exception.ErrorCode;
import gift.global.exception.user.UserDuplicateException;
import gift.global.jwt.JwtProvider;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final JpaUserRepository userRepository;

    public UserService(JpaUserRepository jpaUserRepository) {
        this.userRepository = jpaUserRepository;
    }

    /**
     * 회원 가입
     */
    public void join(UserDTO userDTO) {
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new UserDuplicateException(userDTO.getEmail());
        }

        User user = userDTO.toUser();
        userRepository.save(user);

    }


    /**
     * 로그인, 성공 시 JWT 반환
     */
    public String login(UserDTO userDTO) {
        User user = userRepository.findByEmailAndPassword(userDTO.getEmail(), userDTO.getPassword())
            .orElseThrow(() -> new BusinessException(ErrorCode.BAD_REQUEST, "입력 정보가 올바르지 않습니다."));

        // jwt 토큰 생성
        String jwt = JwtProvider.generateToken(user);

        return jwt;
    }
}
