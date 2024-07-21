# spring-gift-enhancement

## **🚀 Step1 - 상품 카테고리**

---

### 기능 요구 사항

- 상품에는 항상 하나의 카테고리가 있어야 한다.
- 상품 카테고리는 수정할 수 있다.
- 관리자 화면에서 상품을 추가할 때 카테고리를 지정할 수 있다.

💡카테고리는 1차 카테고리만 있으며 2차 카테고리는 고려하지 않는다.

- 카테고리의 예시는 아래와 같다.
    - 교환권, 상품권, 뷰티, 패션, 식품, 리빙/도서, 레저/스포츠, 아티스트/캐릭터, 유아동/반려, 디지털/가전, 카카오프렌즈, 트렌드 선물, 백화점, …

**Request**

```java
GET /api/categories HTTP/1.1
```

**Response**

```java
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

### ToDo

- [X]  Category `vo`, `repository`, `controller`, `service` 작성
- [X]  Category와 연관되는 Test 코드 수정
    - [X]  ProductRepositoryTest.java
    - [X]  WishlistRepositoryTest.java
- [X]  Category Get 구현
- [X]  Category Add 구현
- [X]  Category Update 구현
- [X]  Category Delete 구현
   
## **🚀 Step2 - 상품 옵션**

---

### 기능 요구 사항
- 상품에는 항상 하나 이상의 옵션이 있어야 한다. 
- **옵션 이름** 
  - 공백 포함하여 최대 50자까지 입력할 수 있다. 
  - 조건: 특수 문자 (), [], +, -, &, /, _ 외의 특수 문자 사용 불가 
- 옵션 수량 
  - 최소 1개 이상 1억 개 미만
- 동일 상품 내의 옵션 이름은 중복될 수 없다. 

**Request**
```CURL
GET /api/products/1/options HTTP/1.1
```
**Response**
```CURL
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
### ToDo
- [X] Option: Repository, Service, Entity, Dto 구현
- [X] 옵션 이름 공백 포함 최대 50자 유효성 검사 
- [X] 옵션 이름 특수 문자 ( ), [ ], +, -, &, /, _ 그 외 특수 문자 사용 불가

## **🚀 Step3 - 옵션 수량 차감**

---

### 기능 요구 사항
- 상품 옵션의 수량을 지정된 숫자만큼 빼는 기능을 구현한다. 
  - 별도의 HTTP API를 만들 필요는 없다. 
  - 서비스 클래스 똔는 엔티티 클래스에서 기능을 구현하고 나중에 사용할 수 있도록 한다. 

### ToDo
- [X] 옵션 수량 지정된 숫자만큼 빼는 기능 구현 (서비스 클래스 또는 엔티티 클래스에서)
- [X] Test code 작성 