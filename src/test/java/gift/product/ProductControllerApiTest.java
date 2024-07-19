package gift.product;

import gift.global.exception.custrom.NotFoundException;
import gift.member.business.dto.JwtToken;
import gift.member.presentation.dto.RequestMemberDto;
import gift.product.persistence.entity.Category;
import gift.product.persistence.entity.Product;
import gift.product.persistence.repository.CategoryJpaRepository;
import gift.product.persistence.repository.OptionJpaRepository;
import gift.product.persistence.repository.ProductJpaRepository;
import gift.product.presentation.dto.RequestOptionCreateDto;
import gift.product.presentation.dto.RequestProductDto;
import gift.product.presentation.dto.RequestProductIdsDto;
import gift.product.presentation.dto.RequestProductUpdateDto;
import gift.product.presentation.dto.ResponseProductDto;
import gift.product.persistence.repository.ProductRepository;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class ProductControllerApiTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ProductRepository productRepository;

    private static String accessToken;

    private static HttpHeaders headers;

    private static Category category;

    private static Product dummyProduct;

    @BeforeAll
    static void setUp(@Autowired TestRestTemplate restTemplate,
        @Autowired CategoryJpaRepository categoryRepository,
        @Autowired ProductJpaRepository productRepository,
        @Autowired OptionJpaRepository optionRepository,
        @LocalServerPort int port
    ) {
        //set token
        RequestMemberDto requestMemberDto = new RequestMemberDto(
            "test@example.com",
            "test");

        String url = "http://localhost:" + port + "/api/members/register";
        var response = restTemplate.postForEntity(url, requestMemberDto, JwtToken.class);
        var jwtToken = response.getBody();
        accessToken = jwtToken.accessToken();

        headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        //set category
        category = categoryRepository.save(new Category("카테고리"));

        //set product
        dummyProduct = productRepository.save(new Product("이름", "설명", 1000, "http://test.com", category));
    }

    @Test
    void testCreateProduct() {
        // given
        RequestProductDto requestProductDto = new RequestProductDto(
            "테스트 상품_()[]+-",
            1000,
            "테스트 상품 설명",
            "http://test.com",
            category.getId(),
            List.of(new RequestOptionCreateDto("옵션", 10))
        );

        String url = "http://localhost:"+port+"/api/products";
        var entity = new HttpEntity<>(requestProductDto, headers);

        // when
        var response = restTemplate.exchange(url, HttpMethod.POST, entity, Long.class);

        // then
        assertAll(
            () -> assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED),
            () -> assertThat(response.getBody()).isNotNull()
        );

        Product product = productRepository.getProductById(response.getBody());
        assertAll(
            () -> assertThat(product.getName()).isEqualTo(requestProductDto.name()),
            () -> assertThat(product.getPrice()).isEqualTo(requestProductDto.price()),
            () -> assertThat(product.getDescription()).isEqualTo(requestProductDto.description()),
            () -> assertThat(product.getUrl()).isEqualTo(requestProductDto.imageUrl())
        );
        productRepository.deleteProductById(response.getBody());
    }

    @Test
    void testGetProduct(){
        // given
        String getUrl = "http://localhost:"+port+"/api/products/"+dummyProduct.getId();
        var entity = new HttpEntity<>(headers);

        // when
        var response = restTemplate.exchange(getUrl, HttpMethod.GET, entity, ResponseProductDto.class);

        // then
        assertAll(
            () -> assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK),
            () -> assertThat(response.getBody()).isNotNull()
        );
        ResponseProductDto responseProductDto = response.getBody();
        assertAll(
            () -> assertThat(responseProductDto.id()).isEqualTo(dummyProduct.getId()),
            () -> assertThat(responseProductDto.name()).isEqualTo(dummyProduct.getName()),
            () -> assertThat(responseProductDto.price()).isEqualTo(dummyProduct.getPrice()),
            () -> assertThat(responseProductDto.description()).isEqualTo(dummyProduct.getDescription()),
            () -> assertThat(responseProductDto.imageUrl()).isEqualTo(dummyProduct.getUrl())
        );
    }

    @Test
    void testUpdateProduct(){
        // given
        RequestProductUpdateDto updateProductDto = new RequestProductUpdateDto(
            "수정",
            2000,
            "수정 설명",
            "http://updated.com",
            category.getId()
        );

        String updateUrl = "http://localhost:"+port+"/api/products/"+dummyProduct.getId();

        var entity = new HttpEntity<>(updateProductDto, headers);

        // when
        var response = restTemplate.exchange(updateUrl, HttpMethod.PUT, entity, Long.class);


        // then
        assertAll(
            () -> assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK),
            () -> assertThat(response.getBody()).isNotNull()
        );
        Long updatedId = response.getBody();
        Product product = productRepository.getProductById(updatedId);
        assertAll(
            () -> assertThat(product.getName()).isEqualTo(updateProductDto.name()),
            () -> assertThat(product.getPrice()).isEqualTo(updateProductDto.price()),
            () -> assertThat(product.getDescription()).isEqualTo(updateProductDto.description()),
            () -> assertThat(product.getUrl()).isEqualTo(updateProductDto.imageUrl())

        );
        productRepository.saveProduct(dummyProduct);
    }

    @Test
    void testDeleteProduct() {
        // given
        String deleteUrl = "http://localhost:" + port + "/api/products";

        RequestProductIdsDto requestProductIdsDto = new RequestProductIdsDto(List.of(dummyProduct.getId()));
        var deleteEntity = new HttpEntity<>(requestProductIdsDto, headers);

        // when
        var response = restTemplate.exchange(deleteUrl, HttpMethod.DELETE, deleteEntity, Void.class);

        // then
        assertThrows(NotFoundException.class, () -> productRepository.getProductById(dummyProduct.getId()));

        // rollback
        productRepository.saveProduct(dummyProduct);
    }

    @ParameterizedTest
    @MethodSource("provideTestCases")
    void testCreateProduct_Failure(String name, String imageUrl) {
        // given
        String url = "http://localhost:" + port + "/api/products";

        RequestProductDto requestProductDto = new RequestProductDto(
            name,
            1000,
            "테스트 상품 설명",
            imageUrl,
            category.getId(),
            List.of(new RequestOptionCreateDto("옵션", 10))
        );

        var entity = new HttpEntity<>(requestProductDto, headers);

        // when
        var response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    private static Stream<Arguments> provideTestCases() {
        return Stream.of(
            Arguments.of(null, "http://test.com"),
            Arguments.of("#@", "http://test.com"),
            Arguments.of("카카오", "http://test.com"),
            Arguments.of("asdf", "url 형식 아님")
        );
    }

    @Test
    void testGetProduct_NotFound() {
        // given
        Long nonExistentId = 999L;
        String getUrl = "http://localhost:" + port + "/api/products/" + nonExistentId;

        var entity = new HttpEntity<>(headers);

        // when
        var responseEntity = restTemplate.exchange(getUrl, HttpMethod.GET, entity, String.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void testUpdateProduct_NotFound() {
        // given
        Long nonExistentId = 999L;
        var updateProductDto = new RequestProductUpdateDto("수정된 상품", 2000, "수정된 상품 설명", "http://updated.com", category.getId());
        String updateUrl = "http://localhost:" + port + "/api/products/" + nonExistentId;
        var entity = new HttpEntity<>(updateProductDto, headers);

        // when
        ResponseEntity<String> updateResponseEntity = restTemplate.exchange(updateUrl, HttpMethod.PUT, entity, String.class);

        // then
        assertThat(updateResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void testDeleteProduct_NotFound() {
        // given
        Long nonExistentId = 999L;
        String deleteUrl = "http://localhost:" + port + "/api/products/" + nonExistentId;

        var entity = new HttpEntity<>(headers);

        // when
        ResponseEntity<String> responseEntity = restTemplate.exchange(deleteUrl, HttpMethod.DELETE, entity, String.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void testGetProductByPage_SizeFail(){
        // given
        String getUrl = "http://localhost:"+port+"/api/products?page=0&size=101";

        // when
        var entity = new HttpEntity<>(headers);
        var getResponseEntity = restTemplate.exchange(getUrl, HttpMethod.GET, entity, String.class);

        // then
        assertThat(getResponseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(getResponseEntity.getBody().contains("size는 1~100 사이의 값이어야 합니다.")).isTrue();
    }
}
