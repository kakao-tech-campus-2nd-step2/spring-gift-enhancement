package gift;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

import gift.Model.Category;
import gift.Model.Option;
import gift.Model.Product;
import gift.Repository.OptionRepository;
import gift.Service.OptionService;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class OptionServiceTest {

    @Mock
    private OptionRepository optionRepository;

    @Mock
    private OptionService optionService;

    @Test
    void getAllOptions() {
       // Given
        Category category = new Category(20L, "testCategory", "testColor", "testUrl",
            "testDescription", null);// 1~14번까지 기존 데이터가 존재하므로 20으로 test객체 생성
        Product product = new Product(1L, "test", 1000, "test", category, null);
        Option expect1 = new Option(1L, product, "option1", 100);
        Option expect2 = new Option(2L, product, "option2", 200);
        List<Option> expect = Arrays.asList(expect1, expect2);

        // When getAllOptions 실행되면 expect가 나오도록 설정
        Mockito.when(optionService.getAllOptions(1L)).thenReturn(expect);

        // Then getAllOptions 실행 시 실제 값
        List<Option> actual = optionService.getAllOptions(1L);

        assertEquals(expect, actual);// 검증


    }
    @Test
    void addOptions(){
        // Given
        Product product = new Product(1L,"product",1000,"test",null,null);
        Option expect = new Option(1L, product, "option1", 100);

        // When mokicto는 기본적으로 null을 반환 => 아래 코드로 원래의 값을 반환 (공부필요)
        Mockito.when(optionRepository.save(any(Option.class))).thenAnswer(i -> i.getArguments()[0]);

        // Then
        Option actual = optionRepository.save(expect);

        assertEquals(expect.getId(), actual.getId());
        assertEquals(expect.getProduct(), actual.getProduct());
        assertEquals(expect.getName(), actual.getName());
        assertEquals(expect.getQuantity(), actual.getQuantity());

    }

}
