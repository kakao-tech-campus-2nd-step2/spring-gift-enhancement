package gift.integrity;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import gift.product.dto.OptionDto;
import gift.product.model.Category;
import gift.product.model.Product;
import gift.product.repository.CategoryRepository;
import gift.product.repository.ProductRepository;
import java.net.URI;
import java.util.Map;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class OptionIntegrityTest {

    @LocalServerPort
    int port;

    String BASE_URL = "http://localhost:";

    @Autowired
    TestRestTemplate testRestTemplate;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ProductRepository productRepository;

    @Order(1)
    @Test
    void 옵션_추가() {
        //given
        String url = BASE_URL + port + "/api/options/insert";
        Category category = categoryRepository.save(new Category("테스트카테고리"));
        Product product = productRepository.save(new Product("테스트상품", 1500, "테스트주소", category));
        OptionDto optionDto = new OptionDto("테스트옵션", 1, product.getId());

        RequestEntity<OptionDto> requestEntity = new RequestEntity<>(optionDto, HttpMethod.POST,
            URI.create(url));

        //when
        var actual = testRestTemplate.exchange(requestEntity, String.class);

        //then
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Order(2)
    @Test
    void 옵션_조회() {
        //given
        String url = BASE_URL + port + "/api/options/1";
        RequestEntity<OptionDto> requestEntity = new RequestEntity<>(HttpMethod.GET,
            URI.create(url));

        //when
        var actual = testRestTemplate.exchange(requestEntity, String.class);

        //then
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Order(3)
    @Test
    void 옵션_전체_조회() {
        //given
        String url = BASE_URL + port + "/api/options";
        RequestEntity<OptionDto> requestEntity = new RequestEntity<>(HttpMethod.GET,
            URI.create(url));

        //when
        var actual = testRestTemplate.exchange(requestEntity, String.class);

        //then
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Order(3)
    @Test
    void 특정_상품의_옵션_전체_조회() {
        //given
        String url = BASE_URL + port + "/api/products/1/options";
        RequestEntity<OptionDto> requestEntity = new RequestEntity<>(HttpMethod.GET,
            URI.create(url));

        //when
        var actual = testRestTemplate.exchange(requestEntity, String.class);

        //then
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Order(4)
    @Test
    void 옵션_수정() {
        //given
        String url = BASE_URL + port + "/api/options/update/1";
        OptionDto updatedOptionDto = new OptionDto("테스트옵션수정", 1, 1L);
        RequestEntity<OptionDto> requestEntity = new RequestEntity<>(updatedOptionDto,
            HttpMethod.PUT, URI.create(url));

        //when
        var actual = testRestTemplate.exchange(requestEntity, String.class);

        //then
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Order(5)
    @Test
    void 옵션_삭제() {
        //given
        String url = BASE_URL + port + "/api/options/delete/1";
        RequestEntity<OptionDto> requestEntity = new RequestEntity<>(HttpMethod.DELETE,
            URI.create(url));

        //when
        var actual = testRestTemplate.exchange(requestEntity, String.class);

        //then
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Order(6)
    @Test
    void 옵션_이름이_50자를_초과했을_때() throws JsonProcessingException {
        //given
        String url = BASE_URL + port + "/api/options/insert";
        OptionDto optionDto = new OptionDto("테스트옵션".repeat(51), 1, 1L);

        RequestEntity<OptionDto> requestEntity = new RequestEntity<>(optionDto, HttpMethod.POST,
            URI.create(url));

        //when
        ObjectMapper mapper = new ObjectMapper();
        String responseMessage = testRestTemplate.exchange(requestEntity, String.class).getBody();
        Map<String, Object> responseMessageMap = mapper.readValue(responseMessage, Map.class);

        String message = (String) responseMessageMap.get("detail");

        //then
        assertThat(message).isEqualTo("옵션 이름은 공백 포함 최대 50자까지 입력할 수 있습니다.");
    }

    @Order(7)
    @Test
    void 옵션_이름에_사용_불가능한_특수_문자를_입력했을_때() throws JsonProcessingException {
        //given
        String url = BASE_URL + port + "/api/options/insert";
        OptionDto optionDto = new OptionDto("테스트옵션#", 1, 1L);

        RequestEntity<OptionDto> requestEntity = new RequestEntity<>(optionDto, HttpMethod.POST,
            URI.create(url));

        //when
        ObjectMapper mapper = new ObjectMapper();
        String responseMessage = testRestTemplate.exchange(requestEntity, String.class).getBody();
        Map<String, Object> responseMessageMap = mapper.readValue(responseMessage, Map.class);

        String message = (String) responseMessageMap.get("detail");

        //then
        assertThat(message).isEqualTo("사용 가능한 특수 문자는 ()[]+-&/_ 입니다.");
    }

    @Order(8)
    @Test
    void 옵션_수량이_범위를_초과했을_때() throws JsonProcessingException {
        //given
        String url = BASE_URL + port + "/api/options/insert";
        OptionDto optionDto = new OptionDto("테스트옵션", 100_000_001, 1L);

        RequestEntity<OptionDto> requestEntity = new RequestEntity<>(optionDto, HttpMethod.POST,
            URI.create(url));

        //when
        ObjectMapper mapper = new ObjectMapper();
        String responseMessage = testRestTemplate.exchange(requestEntity, String.class).getBody();
        Map<String, Object> responseMessageMap = mapper.readValue(responseMessage, Map.class);

        String message = (String) responseMessageMap.get("detail");

        //then
        assertThat(message).isEqualTo("옵션 수량은 최소 1개 이상 1억 개 미만이어야 합니다.");
    }
}
