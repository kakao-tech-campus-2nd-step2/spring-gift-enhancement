package gift.repository;

import gift.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DisplayName("멤버 레포지토리 단위테스트")
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;


    private static final String EMAIL = "zzoe2346@git.com";
    private static final String PASSWORD = "12345678";

    @Test
    @DisplayName("이메일과 비밀번호로 멤버 찾기(for Login)")
    void findMemberByEmailAndPassword() {
        //Given
        Member member = new Member(EMAIL, PASSWORD);
        memberRepository.save(member);

        //When
        Optional<Member> foundMember = memberRepository.findByEmailAndPassword(EMAIL, PASSWORD);

        //Then
        assertThat(foundMember).isPresent()
                .hasValueSatisfying(m -> {
                    assertThat(m.getEmail()).isEqualTo(EMAIL);
                    assertThat(m.getId()).isPositive();
                });
    }

    @Test
    @DisplayName("이메일로 멤버 찾기")
    void findByEmail() {
        //Given
        Member member = new Member(EMAIL, PASSWORD);
        Long savedMemberId = memberRepository.save(member).getId();

        //When
        Optional<Member> foundMember = memberRepository.findByEmail(EMAIL);

        //Then
        assertThat(foundMember).isPresent()
                .hasValueSatisfying(m ->
                        assertThat(m.getId()).isEqualTo(savedMemberId)
                );
    }
}
