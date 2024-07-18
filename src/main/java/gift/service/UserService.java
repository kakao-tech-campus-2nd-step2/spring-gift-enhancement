package gift.service;

import gift.exception.ErrorCode;
import gift.exception.customException.CustomNotFoundException;
import gift.model.user.User;
import gift.model.user.UserDTO;
import gift.model.user.UserForm;
import gift.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public Long insertUser(UserForm userForm) {
        return userRepository.save(new User(userForm.getEmail(), userForm.getPassword())).getId();
    }

    @Transactional(readOnly = true)
    public UserDTO findByEmail(String email) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new CustomNotFoundException(ErrorCode.USER_NOT_FOUND));
        return user.toDTO();
    }

    @Transactional(readOnly = true)
    public boolean existsEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Transactional(readOnly = true)
    public boolean isPasswordMatch(UserForm userForm) {
        return userForm.getPassword()
            .equals(userRepository.findByEmail(userForm.getEmail())
                .orElseThrow(() -> new CustomNotFoundException(ErrorCode.USER_NOT_FOUND))
                .getPassword());
    }

    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
