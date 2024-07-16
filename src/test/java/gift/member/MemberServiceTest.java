package gift.member;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import gift.exception.ErrorMessage;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Test
    void register() {
    }

    @Test
    void login() {
    }

    @Nested
    @DisplayName("[Unit] verify password test")
    class VerifyPasswordTest {

        @ParameterizedTest
        @DisplayName("success")
        @MethodSource("success")
        void success(Member member, MemberDTO memberDTO) {
            assertDoesNotThrow(() -> memberService.verifyPassword(member, memberDTO));
        }

        private static Stream<Arguments> success() {
            return Stream.of(
                Arguments.of(
                    new Member("aaa@email.com", "aaa"),
                    new MemberDTO("aaa@email.com", "aaa")
                )
            );
        }

        @ParameterizedTest
        @DisplayName("fail")
        @MethodSource("fail")
        void fail(Member member, MemberDTO memberDTO, String errorMessage) {
            assertThatThrownBy(() -> memberService.verifyPassword(member, memberDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(errorMessage);
        }

        private static Stream<Arguments> fail() {
            return Stream.of(
                Arguments.of(
                    new Member("aaa@email.com", "aaa"),
                    new MemberDTO("aaa@email.com", "aaab"),
                    ErrorMessage.WRONG_PASSWORD
                )
            );
        }

        @ParameterizedTest
        @DisplayName("edge case")
        @MethodSource("edgeCase")
        void edgeCase(Member member, MemberDTO memberDTO) {
            assertDoesNotThrow(() -> memberService.verifyPassword(member, memberDTO));
        }

        // 오직 비밀번호에 한정해서 같은지 여부를 확인하기에, 이메일이 다른 경우에도 통과한다.
        private static Stream<Arguments> edgeCase() {
            return Stream.of(
                Arguments.of(
                    new Member("aaa@email.com", "aaa"),
                    new MemberDTO("bbb@email.com", "aaa")
                )
            );
        }
    }
}