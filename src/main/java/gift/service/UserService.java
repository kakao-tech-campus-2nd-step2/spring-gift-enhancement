package gift.service;

import gift.exception.InvalidUserException;
import gift.exception.UserAlreadyExistException;
import gift.model.user.User;
import gift.repository.UserRepository;
import gift.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;
    private JwtUtil jwtUtil;

    @Autowired
    public UserService(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    public void register(User user) {
        userRepository.findByEmail(user.getEmail()).ifPresent(existUser -> {
            throw new UserAlreadyExistException(existUser.getEmail()+"은 이미 존재하는 이메일입니다.");
        });
        userRepository.save(user);
    }

    public String login(String email, String password) {
        User user = userRepository.findByEmailAndPassword(email, password).orElseThrow(() -> new InvalidUserException("이메일 혹은 패스워드가 유효하지 않습니다."));
        String token = jwtUtil.generateJWT(user);
        return token;
    }

    public boolean validateToken(String token) {
        return jwtUtil.checkValidateToken(token);
    }

    public Optional<User> getUserByToken(String token) {
        try {
            String email = jwtUtil.getUserEmail(token);
            return userRepository.findByEmail(email);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}