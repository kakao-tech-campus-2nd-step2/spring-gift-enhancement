package gift.service;

import gift.dto.UserResponseDto;
import gift.entity.Member;
import gift.repository.UserRepositoryInterface;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.util.List;

@Service
public class UserService {
    private final UserRepositoryInterface userRepositoryInterface;
    private final TokenService tokenService;

    public UserService(UserRepositoryInterface userRepositoryInterface, TokenService tokenService) {
        this.userRepositoryInterface = userRepositoryInterface;
        this.tokenService = tokenService;
    }

    public String save(String email, String password) {

        Member newMember = new Member(email, password);

        Member actualMember = userRepositoryInterface.save(newMember);
        return generateTokenFrom(actualMember.getEmail());
    }


    public List<UserResponseDto> getAll() {
        return userRepositoryInterface.findAll().stream().map(UserResponseDto::fromEntity).toList();
    }

    public String generateTokenFrom(String userEmail) {
        return findUserIdFrom(userEmail).toString();
    }

    private Long findUserIdFrom(String userEmail) {
        return userRepositoryInterface.findByEmail(userEmail).getId();
    }

    public boolean login(String email, String password) throws AuthenticationException {
        UserResponseDto dbUserDto = UserResponseDto.fromEntity(userRepositoryInterface.findByEmail(email));

        return validatePassword(password, dbUserDto.getPassword());
    }

    private boolean validatePassword(String inputPassword, String dbUserPassword) throws AuthenticationException {

        if (inputPassword.equals(dbUserPassword)) {
            return true;
        }
        throw new AuthenticationException("Invalid password");
    }
}
