package gift.controller.gift;

import com.fasterxml.jackson.databind.ObjectMapper;
import gift.dto.gift.GiftRequest;
import gift.dto.gift.GiftResponse;
import gift.dto.option.OptionRequest;
import gift.service.gift.GiftService;
import gift.service.option.OptionService;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class GiftControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GiftService giftService;

    @MockBean
    private OptionService optionService;

    private GiftRequest giftRequest;

    private GiftResponse giftResponse;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        OptionRequest option = new OptionRequest("testOption", 1);
        List<OptionRequest> optionList = Arrays.asList(option);
        giftRequest = new GiftRequest("Test Gift", 1000, "test.jpg", 1L, optionList);
        giftResponse = new GiftResponse(1L, "Test Gift", 1000, "test.jpg", null, null);
        objectMapper = new ObjectMapper();

    }

    @Test
    @DisplayName("상품을 잘 추가하는지 테스트")
    void testAddGift() throws Exception {
        Mockito.when(giftService.addGift(any())).thenReturn(giftResponse);
        String giftRequestJson = objectMapper.writeValueAsString(giftRequest);
        mockMvc.perform(post("/api/gifts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(giftRequestJson))
                .andExpect(status().isCreated())
                .andExpect(content().string("Gift created"));

        Mockito.verify(giftService).addGift(any(GiftRequest.class));
    }

    @Test
    @DisplayName("옵션없이 상품을 추가했을때 오류 메시지가 잘 뜨는지 테스트")
    void testAddGiftNoOption() throws Exception {
        Mockito.when(giftService.addGift(any())).thenReturn(giftResponse);
        giftRequest.setOptions(null);
        String giftRequestJson = objectMapper.writeValueAsString(giftRequest);
        mockMvc.perform(post("/api/gifts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(giftRequestJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.options").value("옵션은 최소 하나 이상 포함되어야 합니다."));
    }

    @Test
    @DisplayName("상품 업데이트 메서드가 잘 동작하는지 테스트")
    void testupdateGift() throws Exception {
        Mockito.doNothing().when(giftService).updateGift(any(), anyLong());

        String giftRequestJson = objectMapper.writeValueAsString(giftRequest);

        mockMvc.perform(put("/api/gifts/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(giftRequestJson))
                .andExpect(status().isOk())
                .andExpect(content().string("상품 수정이 완료되었습니다."));

        Mockito.verify(giftService).updateGift(any(GiftRequest.class), anyLong());
    }

    @Test
    @DisplayName("상품을 삭제하는 메서드가 잘 동작하는지 테스트")
    void testDeleteOptionFromGift() throws Exception {
        Mockito.doNothing().when(giftService).deleteGift(anyLong());

        mockMvc.perform(delete("/api/gifts/{id}", 1L))
                .andExpect(status().isNoContent())
                .andExpect(content().string("상품 삭제가 완료되었습니다"));
    }

}