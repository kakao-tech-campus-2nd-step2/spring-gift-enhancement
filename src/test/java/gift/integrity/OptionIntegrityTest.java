package gift.integrity;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;

import gift.product.dto.CategoryDto;
import gift.product.dto.OptionDto;
import gift.product.model.Category;
import gift.product.model.Product;
import gift.product.repository.CategoryRepository;
import gift.product.repository.ProductRepository;
import java.net.URI;
import java.util.List;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
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
        RequestEntity<OptionDto> requestEntity = new RequestEntity<>(HttpMethod.GET, URI.create(url));

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
        RequestEntity<OptionDto> requestEntity = new RequestEntity<>(HttpMethod.GET, URI.create(url));

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
        RequestEntity<OptionDto> requestEntity = new RequestEntity<>(HttpMethod.GET, URI.create(url));

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
        RequestEntity<OptionDto> requestEntity = new RequestEntity<>(updatedOptionDto, HttpMethod.PUT, URI.create(url));

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
        RequestEntity<OptionDto> requestEntity = new RequestEntity<>(HttpMethod.DELETE, URI.create(url));

        //when
        var actual = testRestTemplate.exchange(requestEntity, String.class);

        //then
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
