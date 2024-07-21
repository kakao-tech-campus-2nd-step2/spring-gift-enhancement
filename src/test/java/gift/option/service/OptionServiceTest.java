//package gift.option.service;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//
//import gift.option.repository.OptionRepository;
//import gift.product.repository.ProductRepository;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.junit.jupiter.api.extension.ExtendWith;
//
//@ExtendWith(MockitoExtension.class)
//public class OptionServiceTest {
//    @Mock
//    private OptionRepository optionRepository;
//
//    @Mock
//    private ProductRepository productRepository;
//
//    @InjectMocks
//    private OptionService optionService;
//
//    @Test
//    // 사용할 수 없는 문자를 옵션명에 사용한 경우 -> 예외 출력
//    public void use_wrong_text_in_optionName() {
//        String wrongOptionName = "invalid!Name";
//
//        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
//            optionService.validateOptionName(wrongOptionName);
//        });
//
//        assertEquals("옵션명에 잘못된 문자가 있습니다.", exception.getMessage());
//    }
//
//    @Test
//    // 옵션명 올바르게 사용
//    public void use_valid_text_in_optionName() {
//        String validOptionName = "Valid_Name(1)";
//
//        optionService.validateOptionName(validOptionName);
//    }
//
//    @Test
//    // 옵션 최소 수량 미만인 경우
//    public void optionQuantity_less_than_1() {
//        int invalidQuantity = 0;
//
//        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
//            optionService.validateOptionQuantity(invalidQuantity);
//        });
//
//        assertEquals("옵션 수량은 최소 1개 이상 1억 개 미만이어야 합니다.", exception.getMessage());
//    }
//
//    @Test
//    // 옵션 최대 수량 초과한 경우
//    public void optionQuantity_more_than_100000000() {
//        int invalidQuantity = 100000000;
//
//        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
//            optionService.validateOptionQuantity(invalidQuantity);
//        });
//
//        assertEquals("옵션 수량은 최소 1개 이상 1억 개 미만이어야 합니다.", exception.getMessage());
//    }
//
//    @Test
//    // 옵션 수량 1개 이상 ~ 1억 개 미만
//    public void optionQuantity_valid() {
//        int validQuantity = 99999999;
//
//        optionService.validateOptionQuantity(validQuantity);
//    }
//}