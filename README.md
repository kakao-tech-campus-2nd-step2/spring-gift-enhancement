## 1단계 - 상품 카테고리

### 기능 요구 사항

#### 카테고리 추가

##### 카테고리 제한사항

- 상품에는 항상 하나의 카테고리가 존재
- 관리자 화면에서 상품 추가시 카테고리도 지정 필수
- 1차 카테고리만 고려

**Request**

`GET /api/categories HTTP/1.1`

**Response**

```json
[
  {
    "id": 91,
    "name": "교환권",
    "color": "#6c95d1",
    "imageUrl": "https://gift-s.kakaocdn.net/dn/gift/images/m640/dimm_theme.png",
    "description": ""
  }
]
```

### 프로그래밍 요구 사항

- 구현한 기능들에대한 테스트 코드 작성

@Test
@DisplayName("생성된 날짜")
void dateCheck() {

        Member member = new Member("a@naver.com", "1234");
        memberRepository.save(member);
        Product product = productRepository.getReferenceById(1L);

        Wish wish = new Wish(member, 100, product);

        Wish save = wishRepository.save(wish);

        assertThat(save.getCreatedTime())
                .isInstanceOf(LocalDateTime.class);
    }

## 2단계 - 상품 옵션

### 기능 요구 사항

#### 옵션 추가

##### 옵션 제한사항

- 상품에는 항상 하나 이상의 옵션이 존재
- 옵션 이름 공백 포함 최대 50자
- 특수문자는 ( ), [ ], +, -, &, /, _ 만 가능
- 옵션 수량은 최소 1 이상 1억 미만
- 동일 상품 동일 옵션 이름 불가
- (선택) 관리자 화면에서 옵션 추가가능

##### 유의

- 제한 사항 꼭 지키기
- 제한사항 어길때의 테스트 적용해보기
- 연관관계 적절히 구성

### 프로그래밍 요구 사항

- 구현한 기능들에대한 테스트 코드 작성

## 3단계 - 옵션 수량 차감

### 기능 요구 사항

#### 옵션 수량 차감

##### 수량을 지정된 숫자만큼 빼기

- 차감 숫자 > 남은 상품 숫자 일때 예외 처리
- 서비스와 엔티티에서 기능을 구현

### 프로그래밍 요구 사항

- 테스트 전략
    - 컨트롤러가 없어도 되는만큼 만들어진 요소들을 모두 단위 테스트에 적합할듯함

## 4단계 - 동시성 테스트

### 기능 요구 사항

#### 1개 남았을때 동시에 차감 요청이 오는 경우

- 차감 숫자 > 남은 상품 숫자 일때 예외 처리를 3단계에서 구현
- 동시성에 대한 테스트 코드 먼저 작성후 이를 기반으로 동시성 문제 발생을 확인
- 동시성 해결 방법에대한 여러 방법이 존재. 이를 분석해보고 적절한것으로 구현
