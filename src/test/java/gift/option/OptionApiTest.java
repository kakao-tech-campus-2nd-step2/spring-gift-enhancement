package gift.option;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

import gift.member.business.dto.JwtToken;
import gift.member.presentation.dto.RequestMemberDto;
import gift.product.persistence.entity.Category;
import gift.product.persistence.entity.Option;
import gift.product.persistence.entity.Product;
import gift.product.persistence.repository.CategoryJpaRepository;
import gift.product.persistence.repository.OptionJpaRepository;
import gift.product.persistence.repository.ProductJpaRepository;
import gift.product.presentation.dto.RequestOptionCreateDto;
import gift.product.presentation.dto.RequestOptionUpdateDto;
import gift.product.presentation.dto.ResponseOptionDto;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class OptionApiTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private OptionJpaRepository optionJpaRepository;

    private static HttpHeaders headers;

    private static Product product;

    private static Option option;

    @BeforeAll
    static void setUp(
        @LocalServerPort int port,
        @Autowired TestRestTemplate restTemplate,
        @Autowired CategoryJpaRepository categoryRepository,
        @Autowired ProductJpaRepository productRepository,
        @Autowired OptionJpaRepository optionRepository
    ) {
        //set token
        RequestMemberDto requestMemberDto = new RequestMemberDto(
            "test@example.com",
            "test");

        String url = "http://localhost:" + port + "/api/members/register";
        var response = restTemplate.postForEntity(url, requestMemberDto, JwtToken.class);
        var jwtToken = response.getBody();

        headers = new HttpHeaders();
        headers.setBearerAuth(jwtToken.accessToken());
        headers.setContentType(MediaType.APPLICATION_JSON);

        //set category, product
        var category = categoryRepository.save(new Category("name"));
        product = productRepository.save(new Product("product", "description", 1000, "http://url.com", category));

        //set option
        option = optionRepository.save(new Option("option", 1, product));
    }

    @Test
    void testCreateOptions() {
        //given
        var requestOptionCreateDto = List.of(
            new RequestOptionCreateDto("option1", 1),
            new RequestOptionCreateDto("option2", 2)
        );
        var url = "http://localhost:" + port + "/api/products/" + product.getId() + "/options";
        var request = new HttpEntity<>(requestOptionCreateDto, headers);

        //when
        var response = restTemplate.postForEntity(url, request, List.class);

        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        optionJpaRepository.deleteAllById(response.getBody());
    }

    @Test
    void testGetOptions() {
        //given
        var url = "http://localhost:" + port + "/api/products/" + product.getId() + "/options";
        var request = new HttpEntity<>(headers);

        //when
        var response = restTemplate.exchange(
            url,
            HttpMethod.GET,
            request,
            new ParameterizedTypeReference<List<ResponseOptionDto>>() {}
        );

        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        var responseOptionDto = response.getBody().getFirst();
        assertAll(
            () -> assertThat(responseOptionDto.name()).isEqualTo(option.getName()),
            () -> assertThat(responseOptionDto.quantity()).isEqualTo(option.getQuantity())
        );
    }

    @Test
    void testUpdateOptions() {
        //given
        var requestOptionUpdateDto = List.of(
            new RequestOptionUpdateDto(
                option.getId(),
                "updatedOption",
                2
            )
        );
        var request = new HttpEntity<>(requestOptionUpdateDto, headers);
        var url = "http://localhost:" + port + "/api/products/" + product.getId() + "/options";

        //when
        var response = restTemplate.exchange(url, HttpMethod.PUT, request, List.class);

        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertAll(
            () -> assertThat(optionJpaRepository.findById(option.getId()).get().getName()).isEqualTo("updatedOption"),
            () -> assertThat(optionJpaRepository.findById(option.getId()).get().getQuantity()).isEqualTo(2)
        );

        optionJpaRepository.save(option);
    }

    @Test
    void testDeleteOptions() {
        //given
        var url = "http://localhost:" + port + "/api/products/options";
        var request = new HttpEntity<>(List.of(product.getId()), headers);

        //when
        var response = restTemplate.exchange(url, HttpMethod.DELETE, request, Void.class);

        //then
        assertThrows(
            NoSuchElementException.class,
            () -> optionJpaRepository.findById(option.getId()).orElseThrow()
        );

        //rollback
        optionJpaRepository.save(option);
    }



}
