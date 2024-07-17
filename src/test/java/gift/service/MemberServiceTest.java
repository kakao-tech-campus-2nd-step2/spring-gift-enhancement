package gift.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import gift.database.JpaMemberRepository;
import gift.dto.MemberDTO;
import gift.model.Member;
import gift.model.MemberRole;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    JpaMemberRepository jpaMemberRepository;

    MemberService memberService;

    public MemberServiceTest(MemberService memberService) {
        this.memberService = memberService;
    }

    @Test
    void register() {

        //given

        MemberDTO memberDTO = new MemberDTO();
        Member memberReturn = new Member(1L,"emaiil","paass", MemberRole.COMMON_MEMBER);

        //when
        memberService.register(memberDTO);
        when(jpaMemberRepository.save(any(Member.class)));

        //then
        memberService.login(memberDTO).getToken();
    }

    @Test
    void login() {
    }

    @Test
    void checkRole() {
    }

    @Test
    void getLoginUser() {
    }
}