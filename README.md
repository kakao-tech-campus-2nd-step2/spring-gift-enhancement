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

- [ ]  Category `vo`, `repository`, `controller`, `service` 작성
- [ ]  Category와 연관되는 Test 코드 수정
    - [ ]  ProductRepositoryTest.java
    - [ ]  WishlistRepositoryTest.java
- [ ]  Category Get 구현
- [ ]  Category Update 구현
- [ ]  Category Delete 구현