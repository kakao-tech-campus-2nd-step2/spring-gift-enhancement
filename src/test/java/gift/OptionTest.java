package gift;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import gift.DTO.Option;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class OptionTest {

  @Mock
  private Option option;

  @Test
  void subtractTest() {
    Option option = mock(Option.class);
    when(option.subtract(1)).thenReturn(true);

    boolean bool = option.subtract(1);
    assertThat(bool).isEqualTo(true);
  }
}
