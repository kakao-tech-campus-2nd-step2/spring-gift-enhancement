package gift.EntityTest;

import gift.Model.Member;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;


public class MemberTest {

    @Test
    void creationTest(){
        Member member = new Member("woo6388@naver.com", "123456789");

        assertAll(
                ()->assertThat(member.getEmail()).isEqualTo("woo6388@naver.com"),
                () -> assertThat(member.getPassword()).isEqualTo("123456789")
        );
    }

    @Test
    void setterTest(){
        Member member = new Member("woo6388@naver.com", "123456789");

        member.setEmail("qoo6388@naver.com");
        member.setPassword("0000");

        assertAll(
                () -> assertThat(member.getEmail()).isEqualTo("qoo6388@naver.com"),
                () -> assertThat(member.getPassword()).isEqualTo("0000")
        );

    }

}
