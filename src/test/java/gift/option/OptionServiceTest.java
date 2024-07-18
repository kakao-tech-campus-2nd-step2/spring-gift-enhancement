package gift.option;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class OptionServiceTest {

    @MockBean
    private OptionRepository optionRepository;

    @Autowired
    private OptionService optionService;

    @Nested
    @DisplayName("[Unit] get options test")
    class getOptionsTest {

    }

    @Nested
    @DisplayName("[Unit] add option test")
    class addOptionTest {

    }

    @Nested
    @DisplayName("[Unit] update option test")
    class updateOptionTest {

    }

    @Nested
    @DisplayName("[Unit] delete option test")
    class deleteOptionTest {

    }
}
