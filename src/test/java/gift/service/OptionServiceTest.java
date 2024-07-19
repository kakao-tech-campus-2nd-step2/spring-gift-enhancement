package gift.service;

import gift.dto.InputProductDTO;
import gift.model.Category;
import gift.model.Option;
import gift.repository.OptionReposityory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

class OptionServiceTest {
    @Mock
    private OptionReposityory optionReposityory;

    @InjectMocks
    private OptionService optionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("옵션 삭제")
    void deleteOption() {
        // given
        List<String> options = new ArrayList<>();
        options.add("option1");
        options.add("option2");
        options.add("option3");
        Option option = new Option(1L, options);
        given(optionReposityory.findById(any()))
                .willReturn(Optional.of(option));
        int beforeSize = option.getOptionList().size();
        // when
        optionService.deleteOption(1L, "option1");
        // then
        then(optionReposityory).should().save(any());
        int afterSize = option.getOptionList().size();
        assertThat(afterSize).isEqualTo(beforeSize-1);
    }

    @Test
    @DisplayName("옵션 추가")
    void addOption() {
        // given
        List<String> options = new ArrayList<>();
        options.add("option1");
        options.add("option2");
        options.add("option3");
        Option option = new Option(1L, options);
        given(optionReposityory.findById(any()))
                .willReturn(Optional.of(option));
        int beforeSize = option.getOptionList().size();
        // when
        optionService.addOption(1L, "newOption");
        // then
        then(optionReposityory).should().save(any());
        int afterSize = option.getOptionList().size();
        assertThat(afterSize).isEqualTo(beforeSize+1);
    }

    @Test
    @DisplayName("옵션 수정")
    void updateOption() {
        // given
        List<String> options = new ArrayList<>();
        options.add("option1");
        options.add("option2");
        options.add("option3");
        Option option = new Option(1L, options);
        given(optionReposityory.findById(any()))
                .willReturn(Optional.of(option));
        int beforeSize = option.getOptionList().size();
        // when
        optionService.updateOption(1L, "option1", "newOption");
        // then
        then(optionReposityory).should().save(any());
        int afterSize = option.getOptionList().size();
        assertThat(afterSize).isEqualTo(beforeSize);
    }

    @Test
    @DisplayName("옵션 n개 삭제")
    void removeOption() {
        // given
        List<String> options = new ArrayList<>();
        options.add("option1");
        options.add("option2");
        options.add("option3");
        Option option = new Option(1L, options);
        given(optionReposityory.findById(any()))
                .willReturn(Optional.of(option));
        int beforeSize = option.getOptionList().size();
        int removeNum = 2;
        // when
        optionService.removeOption(1L, removeNum);
        // then
        then(optionReposityory).should().save(any());
        int afterSize = option.getOptionList().size();
        assertThat(afterSize).isEqualTo(beforeSize-removeNum);
    }
}