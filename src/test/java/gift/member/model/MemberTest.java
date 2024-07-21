package gift.member.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MemberTest {

    private Member member;

    @BeforeEach
    void setUp() {
        member = new Member("test@example.com", "password");
    }

    @Test
    void testUpdateEmail() {
        member.updateEmail("new@example.com");
        assertEquals("new@example.com", member.getEmail());
    }

    @Test
    void testUpdateEmailWithInvalidEmail() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            member.updateEmail("");
        });
        assertEquals("이메일은 비어있을 수 없습니다.", exception.getMessage());
    }

    @Test
    void testUpdatePassword() {
        member.updatePassword("newPassword");
        assertEquals("newPassword", member.getPassword());
    }

    @Test
    void testUpdatePasswordWithInvalidPassword() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            member.updatePassword("");
        });
        assertEquals("비밀번호는 비어있을 수 없습니다.", exception.getMessage());
    }

    @Test
    void testCheckPassword() {
        assertTrue(member.checkPassword("password"));
        assertFalse(member.checkPassword("wrongPassword"));
    }

    @Test
    void testValidateLoginWithCorrectPassword() {
        assertDoesNotThrow(() -> {
            member.validateLogin("password");
        });
    }

    @Test
    void testValidateLoginWithIncorrectPassword() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            member.validateLogin("wrongPassword");
        });
        assertEquals("옳지 않은 비밀번호 입니다.", exception.getMessage());
    }
}