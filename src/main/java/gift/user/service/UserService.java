package gift.user.service;

import gift.exception.InvalidTokenException;
import gift.exception.user.UserAlreadyExistException;
import gift.exception.user.UserNotFoundException;
import gift.user.dto.request.UserLoginRequest;
import gift.user.dto.request.UserRegisterRequest;
import gift.user.dto.response.UserResponse;
import gift.user.entity.Role;
import gift.user.entity.User;
import gift.user.repository.RoleRepository;
import gift.user.repository.UserRepository;
import gift.util.auth.JwtUtil;
import gift.util.mapper.UserMapper;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserService(JwtUtil jwtUtil, UserRepository userRepository,
        RoleRepository roleRepository) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Transactional
    public UserResponse registerUser(UserRegisterRequest request) {
        userRepository.findByEmail(request.email())
            .ifPresent(user -> {
                throw new UserAlreadyExistException("이미 존재하는 Email입니다.");
            });
        User registeredUser = userRepository.save(UserMapper.toUser(request));

        return UserMapper.toResponse(jwtUtil.generateToken(registeredUser.getId(),
            registeredUser.getEmail(), null));
    }

    @Transactional(readOnly = true)
    public UserResponse loginUser(UserLoginRequest userRequest) {
        User user = userRepository.findByEmailAndPassword(userRequest.email(),
                userRequest.password())
            .orElseThrow(() -> new UserNotFoundException("로그인할 수 없습니다."));
        List<Long> roleIds = user.getRoles()
            .stream()
            .map(userRole -> userRole.getRole().getId())
            .toList();

        List<String> roles = roleRepository.findAllById(roleIds)
            .stream()
            .map(Role::getName)
            .toList();

        String token = jwtUtil.generateToken(user.getId(), user.getEmail(), roles);

        return UserMapper.toResponse(token);
    }

    @Transactional(readOnly = true)
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다."));
    }

    @Transactional(readOnly = true)
    public Long getUserIdByToken(String token) {
        Long userId = jwtUtil.extractUserId(token);
        if (userId == null || !userRepository.existsById(userId)) {
            throw new InvalidTokenException();
        }
        return userId;
    }

}
