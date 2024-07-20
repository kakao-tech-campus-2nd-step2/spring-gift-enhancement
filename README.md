# spring-gift-enhancement

## 구현할 기능 목록

### 1단계 - 상품 카테고리

TO DO
- [x] 카테코리 엔티티 클래스 정의
- [x] 상품 정보에 카테고리 추가


- [x] 카테고리 기본 CRUD
- [x] 카테고리에 속한 상품 조회 - 테스트 코드
- [x] 상품을 등록할 떼 카테고리 설정
- [x] 상품의 카테고리 수정 ex) A 상품의 카테고리를 '교환권' 카테고리에서 '과제면회권' 카테고리로 변경한다

고려해야할 사항
- 상품에는 항상 하나의 카테고리가 있어야 한다.
    - 상품 카테고리는 수정할 수 있다.
    - 관리자 화면에서 상품을 추가할 때 카테고리를 지정할 수 있다.
- 카테고리는 1차 카테고리만 있으며 2차 카테고리는 고려하지 않는다.
- 카테고리의 예시는 아래와 같다.
  - 교환권, 상품권, 뷰티, 패션, 식품, 리빙/도서, 레저/스포츠, 아티스트/캐릭터, 유아동/반려, 디지털/가전, 카카오프렌즈, 트렌드 선물, 백화점, ...

**Request**
```http request
GET /api/categories HTTP/1.1
```

**Response**
```http request
HTTP/1.1 200 
Content-Type: application/json

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

### 2단계 - 상품 옵션

- [x] 옵션 엔티티 클래스 정의
- [x] 상품 정보에 옵션 추가

고려해야할 사항

- 상품에는 항상 하나 이상의 옵션이 있어야 한다.
  - 옵션 이름은 공백을 포함하여 최대 50자까지 입력할 수 있다.
  - 특수 문자
    - 가능: ( ), [ ], +, -, &, /, _
    - 그 외 특수 문자 사용 불가
  - 옵션 수량은 최소 1개 이상 1억 개 미만이다.
- 중복된 옵션은 구매 시 고객에게 불편을 줄 수 있다. 동일한 상품 내의 옵션 이름은 중복될 수 없다.

**Request**
```http request
GET /api/products/1/options HTTP/1.1
```

**Response**
```http request
HTTP/1.1 200 
Content-Type: application/json

[
  {
    "id": 464946561,
    "name": "01. [Best] 시어버터 핸드 & 시어 스틱 립 밤",
    "quantity": 969
  }
]
```
