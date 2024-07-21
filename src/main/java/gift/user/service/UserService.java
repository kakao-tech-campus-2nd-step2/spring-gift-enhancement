package gift.user.service;

import gift.exception.CustomException;
import gift.exception.ErrorCode;
import gift.user.dto.request.UserLoginRequest;
import gift.user.dto.request.UserRegisterRequest;
import gift.user.dto.response.UserResponse;
import gift.user.entity.Role;
import gift.user.entity.User;
import gift.user.repository.RoleJpaRepository;
import gift.user.repository.UserJpaRepository;
import gift.util.auth.JwtUtil;
import gift.util.mapper.UserMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final JwtUtil jwtUtil;
    private final UserJpaRepository userRepository;
    private final RoleJpaRepository roleRepository;

    public UserService(JwtUtil jwtUtil, UserJpaRepository userRepository,
        RoleJpaRepository roleRepository) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Transactional
    public UserResponse registerUser(UserRegisterRequest request) {
        userRepository.findByEmail(request.email())
            .ifPresent(user -> {
                throw new CustomException(ErrorCode.USER_ALREADY_EXIST);
            });
        User registeredUser = userRepository.save(UserMapper.toUser(request));
        List<String> roles = new ArrayList<>();

        return UserMapper.toResponse(jwtUtil.generateToken(registeredUser.getId(),
            registeredUser.getEmail(), roles));
    }

    @Transactional(readOnly = true)
    public UserResponse loginUser(UserLoginRequest userRequest) {
        User user = userRepository.findByEmailAndPassword(userRequest.email(),
                userRequest.password())
            .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        List<Long> roleIds = user.getRoles()
            .stream()
            .filter(Objects::nonNull)
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
            .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public Long getUserIdByToken(String token) {
        Long userId = jwtUtil.extractUserId(token);
        if (userId == null || !userRepository.existsById(userId)) {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }
        return userId;
    }

}
