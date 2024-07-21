package gift.intergrationTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import gift.exception.ErrorCode;
import gift.interceptor.AuthInterceptor;
import gift.model.categories.Category;
import gift.model.item.Item;
import gift.model.item.ItemForm;
import gift.model.option.Option;
import gift.model.option.OptionDTO;
import gift.repository.CategoryRepository;
import gift.repository.ItemRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;


@AutoConfigureMockMvc
@SpringBootTest
@TestMethodOrder(MethodOrderer.DisplayName.class)
public class ItemWithOptionTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthInterceptor authInterceptor;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ItemRepository itemRepository;


    private final String testCategoryName = "가구";
    private final String testItemName = "의자";
    private final String testUrl = "img";
    private final Long testQuantity = 2000L;
    private final Long testPrice = 1000L;
    private final String duplicatedName = "흰색";
    private final String wrongName = "%$#%$잘못된 옵션이름";
    private OptionDTO option1 = new OptionDTO("흰색", 100L);
    private OptionDTO option2 = new OptionDTO("검정색", 50L);
    private OptionDTO option3 = new OptionDTO("핑크색", 200L);
    private Category testCategory = new Category(testCategoryName, testUrl);

    @BeforeEach
    void setUp() throws Exception {
        given(authInterceptor.preHandle(any(), any(), any())).willReturn(true);
    }

    ItemForm setItemForm(OptionDTO option1, OptionDTO option2, OptionDTO option3) {
        Long categoryId = categoryRepository.findByName(testCategoryName).get().getId();
        List<OptionDTO> options = new ArrayList<>(
            List.of(option1, option2, option3));
        return new ItemForm(testItemName, testPrice, testUrl, categoryId, options);
    }


    @DisplayName("1. 상품과 옵션 동시 추가 실패(잘못된 옵션 명)")
    @Test
    void testInsertItemWithWrongNameOptions() throws Exception {
        categoryRepository.save(testCategory);
        OptionDTO wrongOption = new OptionDTO(wrongName, testQuantity);
        ItemForm itemForm = setItemForm(option1, wrongOption, option3);

        String content = objectMapper.writeValueAsString(itemForm);

        mockMvc.perform(post("/product").contentType(MediaType.APPLICATION_JSON).content(content))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value(ErrorCode.INVALID_INPUT.getMessage()))
            .andDo(print());
    }

    @DisplayName("2. 상품과 옵션 동시 추가 실패(중복되는 옵션)")
    @Test
    void testInsertItemWithDuplicateNameOptions() throws Exception {
        ItemForm itemForm = setItemForm(option1, option1, option3);
        String content = objectMapper.writeValueAsString(itemForm);

        mockMvc.perform(post("/product").contentType(MediaType.APPLICATION_JSON).content(content))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value(ErrorCode.DUPLICATE_NAME.getMessage()))
            .andDo(print());
    }

    @DisplayName("3. 상품과 옵션 동시 추가 성공 테스트")
    @Test
    void testInsertItemWithOptionsSuccess() throws Exception {
        ItemForm itemForm = setItemForm(option1, option2, option3);
        String content = objectMapper.writeValueAsString(itemForm);

        mockMvc.perform(post("/product").contentType(MediaType.APPLICATION_JSON).content(content))
            .andExpect(status().isOk())
            .andDo(print());
    }

    @DisplayName("4. 옵션 단독 추가 실패 테스트(잘못된 이름)")
    @Test
    void testInsertWrongOption() throws Exception {
        Long itemId = itemRepository.findAll().get(0).getId();
        OptionDTO optionDTO = new OptionDTO(wrongName, testQuantity);
        String content = objectMapper.writeValueAsString(optionDTO);

        mockMvc.perform(
                post("/option/" + itemId).contentType(MediaType.APPLICATION_JSON).content(content))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value(ErrorCode.INVALID_INPUT.getMessage()))
            .andDo(print());
    }

    @DisplayName("5. 옵션 단독 추가 실패 테스트(중복된 이름)")
    @Test
    void testInsertDuplicateOption() throws Exception {
        Long itemId = itemRepository.findAll().get(0).getId();
        OptionDTO optionDTO = new OptionDTO(duplicatedName, testQuantity);
        String content = objectMapper.writeValueAsString(optionDTO);

        mockMvc.perform(
                post("/option/" + itemId).contentType(MediaType.APPLICATION_JSON).content(content))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value(ErrorCode.DUPLICATE_NAME.getMessage()))
            .andDo(print());
    }

    @DisplayName("6. 옵션 단독 추가 성공 테스트")
    @Test
    void testInsertOptionSuccess() throws Exception {
        Long itemId = itemRepository.findAll().get(0).getId();
        OptionDTO optionDTO = new OptionDTO("회색", testQuantity);
        String content = objectMapper.writeValueAsString(optionDTO);

        mockMvc.perform(
                post("/option/" + itemId).contentType(MediaType.APPLICATION_JSON).content(content))
            .andExpect(status().isOk())
            .andDo(print());
    }

    @Transactional
    @DisplayName("7. 옵션 단독 수정 성공 테스트")
    @Test
    void testUpdateOptionSuccess() throws Exception {
        Item item = itemRepository.findAll().get(0);
        Option option = item.getOptions().stream().filter(o -> o.getName().equals("회색")).findFirst()
            .get();
        OptionDTO optionDTO = new OptionDTO(option.getId(), "업데이트 된 회색", testQuantity + 20L);
        String content = objectMapper.writeValueAsString(optionDTO);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/option/" + item.getId())
                    .contentType(MediaType.APPLICATION_JSON).content(content))
            .andExpect(status().isOk())
            .andDo(print());

        mockMvc.perform(
                get("/option/" + item.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andDo(print());
    }

    @DisplayName("8. 옵션 목록 조회 테스트")
    @Test
    void testFindOptionsSuccess() throws Exception {
        Long itemId = itemRepository.findAll().get(0).getId();

        mockMvc.perform(
                get("/option/" + itemId))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andDo(print());
    }


    @DisplayName("9. 상품 삭제시 옵션도 삭제 테스트")
    @Test
    void testDeleteItemWithOptionsSuccess() throws Exception {
        Long itemId = itemRepository.findAll().get(0).getId();

        mockMvc.perform(
                delete("/product/" + itemId))
            .andExpect(status().isOk())
            .andDo(print());
    }

}
