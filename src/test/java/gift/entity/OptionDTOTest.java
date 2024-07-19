package gift.entity;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

@SpringBootTest
public class OptionDTOValidationTest {

    @Autowired
    private Validator validator;

    @Test
    void 상품_이름이_50자가_넘는_경우() {
        //given
        OptionDTO option = new OptionDTO("a".repeat(51), 100);

        //when
        Set<ConstraintViolation<OptionDTO>> violations = validator.validate(option);

        //then
        Assertions.assertThat(violations).isNotEmpty();
    }

    @Test
    void 상품에_불가능한_특수문자가_있는_경우() {
        //given
        OptionDTO option = new OptionDTO("옵̶̦̞̜̹͇̳̺͚͆͐͌͗̄ͅ션̷͉̩͍̪̪̄̂̂̐̄̕", 100);

        //when
        Set<ConstraintViolation<OptionDTO>> violations = validator.validate(option);

        //then
        Assertions.assertThat(violations).isNotEmpty();
    }

    @Test
    void 상품_옵션_수량이_0개인_경우() {
        //given
        OptionDTO option = new OptionDTO("option", 0);

        //when
        Set<ConstraintViolation<OptionDTO>> violations = validator.validate(option);

        //then
        Assertions.assertThat(violations).isNotEmpty();
    }

    @Test
    void 상품_옵션_수량이_1억개인_경우() {
        //given
        OptionDTO option = new OptionDTO("option", 100_000_000);

        //when
        Set<ConstraintViolation<OptionDTO>> violations = validator.validate(option);

        //then
        Assertions.assertThat(violations).isNotEmpty();
    }

    @Test
    void 성공_케이스() {
        //given
        OptionDTO option = new OptionDTO("abc ()[]+-&/_ 가나다", 100);

        //when
        Set<ConstraintViolation<OptionDTO>> violations = validator.validate(option);

        //then
        Assertions.assertThat(violations).isEmpty();
    }
}
