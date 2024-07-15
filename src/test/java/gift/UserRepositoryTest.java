package gift;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.*;

import gift.model.User;
import gift.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void save() {
        User expected = new User(null, "test@example.com", "password");
        User actual = userRepository.save(expected);
        assertAll(
            () -> assertThat(actual.getId()).isNotNull(),
            () -> assertThat(actual.getEmail()).isEqualTo(expected.getEmail())
        );
    }

    @Test
    void findByEmail() {
        String expectedEmail = "test@example.com";
        User expected = new User(null, expectedEmail, "password");
        userRepository.save(expected);
        User actual = userRepository.findByEmail(expectedEmail);
        assertThat(actual).isNotNull();
        assertThat(actual.getEmail()).isEqualTo(expectedEmail);
    }
}