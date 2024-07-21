package gift.unit.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;

import gift.exception.CustomException;
import gift.user.dto.request.UserLoginRequest;
import gift.user.dto.request.UserRegisterRequest;
import gift.user.dto.response.UserResponse;
import gift.user.entity.User;
import gift.user.entity.UserRole;
import gift.user.repository.RoleJpaRepository;
import gift.user.repository.UserJpaRepository;
import gift.user.service.UserService;
import gift.util.auth.JwtUtil;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserJpaRepository userRepository;

    @Mock
    private RoleJpaRepository roleRepository;

    @Mock
    private JwtUtil jwtUtil;

    @Test
    @DisplayName("register user test")
    @Transactional
    void registerUserTest() {
        //given
        UserRegisterRequest request = new UserRegisterRequest("user@email.com", "1q2w3e4r!");
        given(userRepository.findByEmail(request.email())).willReturn(Optional.empty());
        User user = User.builder()
            .id(1L)
            .email(request.email())
            .password(request.password())
            .build();
        List<String> roles = new ArrayList<>();
        given(userRepository.save(any(User.class))).willReturn(user);
        given(jwtUtil.generateToken(user.getId(), user.getEmail(), roles)).willReturn("token");

        //when
        UserResponse actual = userService.registerUser(request);

        //then
        assertThat(actual.token()).isEqualTo("token");
        then(userRepository).should(times(1)).findByEmail(request.email());
        then(userRepository).should(times(1)).save(any(User.class));
        then(jwtUtil).should(times(1)).generateToken(user.getId(), user.getEmail(), roles);
    }

    @Test
    @DisplayName("Already Exist user registration test")
    @Transactional
    void alreadyExistUserRegistrationTest() {
        //given
        UserRegisterRequest request = new UserRegisterRequest("user1@example.com", "password1");
        given(userRepository.findByEmail(request.email())).willReturn(
            Optional.of(User.builder().build()));

        //when&then
        assertThatThrownBy(() -> userService.registerUser(request))
            .isInstanceOf(CustomException.class);
        then(userRepository).should(times(1)).findByEmail(request.email());
    }

    @Test
    @DisplayName("user login test")
    @Transactional
    void userLoginTest() {
        //given
        UserLoginRequest loginRequest = new UserLoginRequest("user1@example.com", "password1");
        Set<UserRole> roles = new HashSet<>();
        User user = User.builder()
            .id(1L)
            .email(loginRequest.email())
            .password(loginRequest.password())
            .userRoles(roles)
            .build();
        List<String> rolesName = new ArrayList<>();
        given(userRepository.findByEmailAndPassword(loginRequest.email(), loginRequest.password()))
            .willReturn(Optional.of(user));
        given(jwtUtil.generateToken(user.getId(), user.getEmail(), rolesName)).willReturn("token");
        given(roleRepository.findAllById(any())).willReturn(List.of());

        //when
        UserResponse actual = userService.loginUser(loginRequest);

        //then
        assertThat(actual.token()).isEqualTo("token");
        then(userRepository).should(times(1)).findByEmailAndPassword(loginRequest.email(),
            loginRequest.password());
    }

    @Test
    @DisplayName("unknown user login test")
    @Transactional
    void unknownUserLoginTest() {
        //given
        UserLoginRequest request = new UserLoginRequest("user1@email.com", "1q2w3e4r!");
        given(
            userRepository.findByEmailAndPassword(request.email(), request.password())).willReturn(
            Optional.empty());

        //when & then
        assertThatThrownBy(() -> userService.loginUser(request))
            .isInstanceOf(CustomException.class);
    }

    @Test
    @DisplayName("wrong password login test")
    @Transactional
    void wrongPasswordLoginTest() {
        //given
        UserLoginRequest request = new UserLoginRequest("user1@email.com", "1234");
        given(
            userRepository.findByEmailAndPassword(request.email(), request.password())).willReturn(
            Optional.empty());

        //when & then
        assertThatThrownBy(() -> userService.loginUser(request))
            .isInstanceOf(CustomException.class);
        then(userRepository).should(times(1)).findByEmailAndPassword(request.email(),
            request.password());
    }

    @Test
    @DisplayName("get user by id test")
    void getUserByIdTest() {
        // given
        User user = User.builder()
            .id(1L)
            .email("user1@example.com")
            .password("password1")
            .build();
        given(userRepository.findById(1L)).willReturn(Optional.of(user));

        // when
        User actual = userService.getUserById(1L);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isEqualTo(1L);
        then(userRepository).should(times(1)).findById(1L);
    }

    @Test
    @DisplayName("get user by id not found test")
    void getUserByIdNotFoundTest() {
        // given
        given(userRepository.findById(1L)).willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> userService.getUserById(1L))
            .isInstanceOf(CustomException.class);
        then(userRepository).should(times(1)).findById(1L);
    }

    @Test
    @DisplayName("get user id by token test")
    void getUserIdByTokenTest() {
        // given
        String token = "token";
        given(jwtUtil.extractUserId(token)).willReturn(1L);
        given(userRepository.existsById(1L)).willReturn(true);

        // when
        Long userId = userService.getUserIdByToken(token);

        // then
        assertThat(userId).isEqualTo(1L);
        then(jwtUtil).should(times(1)).extractUserId(token);
        then(userRepository).should(times(1)).existsById(1L);
    }

    @Test
    @DisplayName("invalid token test")
    void invalidTokenTest() {
        // given
        String token = "invalid_token";
        given(jwtUtil.extractUserId(token)).willReturn(null);

        // when & then
        assertThatThrownBy(() -> userService.getUserIdByToken(token))
            .isInstanceOf(CustomException.class);
        then(jwtUtil).should(times(1)).extractUserId(token);
    }
}
