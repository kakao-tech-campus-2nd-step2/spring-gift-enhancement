package gift;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

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
  void subtractTest() throws IllegalAccessException {
    Option option = mock(Option.class);
    doThrow(IllegalAccessException.class).when(option).subtract(3);

    assertThrows(IllegalAccessException.class, () -> {
      option.subtract(3);
    });
  }
}
