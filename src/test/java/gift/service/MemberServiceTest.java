package gift.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;

import gift.dto.MemberDTO;
import gift.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MemberServiceTest {
    private MemberDTO memberDTO;

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void setUp() throws Exception {
        memberDTO = new MemberDTO("1234@1234.com", "1234");
    }

    @Test
    void register() {
        memberService.register(memberDTO);

        assertThat(memberRepository.findByEmail("1234@1234.com")).isNotNull();
    }

    @Test
    void login() {
        memberService.register(memberDTO);

        assertThatNoException().isThrownBy(() -> memberService.login(memberDTO));
    }

    @Test
    void getMember() {
        memberRepository.save(memberDTO.convertToMember());

        assertThat(memberService.getMember(memberDTO.getEmail())).isNotNull();
    }
}