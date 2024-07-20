package gift.integrationTest;

import static org.assertj.core.api.Assertions.assertThat;

import gift.domain.controller.apiResponse.ProductAddApiResponse;
import gift.domain.controller.apiResponse.ProductListApiResponse;
import gift.domain.dto.request.OptionRequest;
import gift.domain.dto.request.ProductAddRequest;
import gift.domain.dto.request.ProductUpdateRequest;
import gift.domain.dto.response.ProductResponse;
import gift.domain.dto.response.ProductWithCategoryIdResponse;
import gift.global.apiResponse.BasicApiResponse;
import gift.utilForTest.TestUtil;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.transaction.annotation.Transactional;

//TODO: 옵션 관련 기능 구현 후 테스트 고치기
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Transactional
class ProductDomainTest {

    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private TestUtil testUtil;

    private List<ProductWithCategoryIdResponse> getProductListByRequest() {
        return Objects.requireNonNull(restTemplate.exchange(
            new RequestEntity<>(null, new HttpHeaders(), HttpMethod.GET, testUtil.getUri(port, "/api/products")),
            ProductListApiResponse.class).getBody()).getProducts();
    }

    private ProductAddRequest getProductRequest() {
        return new ProductAddRequest(
            "product",
            1_000,
            "image.jpg",
            1L,
            List.of(new OptionRequest("옵션1", 300)));
    }

    @Test
    @DisplayName("[ApiIntegrationTest] 상품 리스트 조회")
    void getProducts() {
        //given
        List<List<OptionRequest>> optionRequests = new ArrayList<>(List.of(
            List.of(new OptionRequest("옵션1", 300)),
            List.of(new OptionRequest("옵션1", 400)),
                List.of(new OptionRequest("옵션2", 500))));
        List<ProductAddRequest> request = new ArrayList<>(List.of(
            new ProductAddRequest("product1", 1_000, "image1.jpg", 1L, optionRequests.get(0)),
            new ProductAddRequest("product2", 2_000, "image2.jpg", 1L, optionRequests.get(1)),
            new ProductAddRequest("product3", 3_000, "image3.jpg", 1L, optionRequests.get(2))));
        request.sort(Comparator.comparing(ProductAddRequest::name));
        for (var req: request) {
            // request로 상품 등록 API 호출해 상품들을 등록함
            restTemplate.exchange(
                new RequestEntity<>(req, HttpMethod.POST, testUtil.getUri(port, "/api/products")),
                ProductAddApiResponse.class);
        }

        //when
        var actualResponse =
            restTemplate.exchange(
                new RequestEntity<>(null, new HttpHeaders(), HttpMethod.GET, testUtil.getUri(port, "/api/products")),
                ProductListApiResponse.class);

        //then
        assertThat(actualResponse.getStatusCode())
            .isEqualTo(HttpStatus.OK);
        List<ProductWithCategoryIdResponse> response = getProductListByRequest();
        for (var req: request) {
            ProductWithCategoryIdResponse actual = response.stream().filter(r -> r.name().equals(req.name())).findAny().get();
            assertThat(actual.name()).isEqualTo(req.name());
            assertThat(actual.price()).isEqualTo(req.price());
            assertThat(actual.imageUrl()).isEqualTo(req.imageUrl());
            //TODO: 카테고리, 옵션응답에 대한 검증 필요 (카테고리 컨트롤러 구현필요)
            //assertThat(actual.category()).isEqualTo()
        }
    }

    @Test
    @DisplayName("[ApiIntegrationTest] 상품 추가")
    void addProduct() {
        //given
        ProductAddRequest request = getProductRequest();

        //when
        var actualResponse = restTemplate.exchange(
            new RequestEntity<>(request, HttpMethod.POST, testUtil.getUri(port, "/api/products")),
            ProductAddApiResponse.class);

        //then
        assertThat(actualResponse.getStatusCode())
            .isEqualTo(HttpStatus.CREATED);
        assertThat(actualResponse.getHeaders().containsKey(HttpHeaders.LOCATION))
            .isTrue();
        ProductResponse createdProduct = Objects.requireNonNull(actualResponse.getBody()).getCreatedProduct();
        assertThat(createdProduct.name()).isEqualTo(request.name());
        assertThat(createdProduct.price()).isEqualTo(request.price());
        assertThat(createdProduct.imageUrl()).isEqualTo(request.imageUrl());
        //TODO: 카테고리 컨트롤러 추가후 카테고리 검증 필요
    }

    @Test
    @DisplayName("[ApiIntegrationTest] 상품 수정")
    void updateProduct() {
        //given
        ProductAddRequest request = getProductRequest();
        Long createdId = Objects.requireNonNull(restTemplate.exchange(
            new RequestEntity<>(request, HttpMethod.POST, testUtil.getUri(port, "/api/products")),
            ProductAddApiResponse.class).getBody()).getCreatedProduct().id();
        ProductUpdateRequest toUpdateRequest = new ProductUpdateRequest("newProductName", 5_000, "newImage.jpg", 1L);

        //when
        var actualResponse = restTemplate.exchange(
            new RequestEntity<>(toUpdateRequest, HttpMethod.PUT, testUtil.getUri(port, "/api/products/{id}", createdId)),
            BasicApiResponse.class);

        //then
        assertThat(actualResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        //Product List를 요청한 뒤 given에서 생성된 product를 추출
        var updatedProduct = getProductListByRequest().stream()
                .filter(p -> p.id().equals(createdId)).findAny().get();

        assertThat(updatedProduct.name()).isEqualTo(toUpdateRequest.name());
        assertThat(updatedProduct.price()).isEqualTo(toUpdateRequest.price());
        assertThat(updatedProduct.imageUrl()).isEqualTo(toUpdateRequest.imageUrl());
        //TODO: 카테고리, 옵션 검증 필요
    }

    //TODO: 카테고리 수정 테스트 필요
    //TODO: 옵션 CRUD 테스트 필요

    @Test
    @DisplayName("[ApiIntegrationTest] 상품 삭제")
    void deleteProduct() {
        //given
        ProductAddRequest request = getProductRequest();
        Long createdId = Objects.requireNonNull(restTemplate.exchange(
            new RequestEntity<>(request, HttpMethod.POST, testUtil.getUri(port, "/api/products")),
            ProductAddApiResponse.class).getBody()).getCreatedProduct().id();

        //when
        var actualResponse = restTemplate.exchange(
            new RequestEntity<>(null, new HttpHeaders(), HttpMethod.DELETE, testUtil.getUri(port, "/api/products/{id}", createdId)),
            BasicApiResponse.class);

        //then
        assertThat(actualResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getProductListByRequest().stream().filter(p -> p.id().equals(createdId)).findAny())
            .isEqualTo(Optional.empty());
    }
}