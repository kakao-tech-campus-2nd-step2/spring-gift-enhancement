# spring-gift-enhancement
## Step1 - 상품 카테고리
### 기능 요구사항
상품 정보에 카테고리를 추가한다. 상품과 카테고리 모델 간의 관계를 고려하여 설계하고 구현한다.
- 카테고리는 1차 카테고리만 있으며 2차 카테고리는 고려하지 않는다.
- 카테고리는 수정할 수 있다.
- 관리자 화면에서 상품을 추가할 때 카테고리를 지정할 수 있다.
- 카테고리의 예시는 아래와 같다.
    - 교환권, 상품권, 뷰티, 패션, 식품, 리빙/도서, 레저/스포츠, 아티스트/캐릭터, 유아동/반려, 디지털/가전, 카카오프렌즈, 트렌드 선물, 백화점
      아래 예시와 같이 HTTP 메시지를 주고받도록 구현한다.
#### Request
```http
GET /api/categories HTTP/1.1
```
#### Response
```http
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
### 프로그래밍 요구사항
- 구현한 기능에 대해 적절한 테스트 전략을 생각하고 작성한다.

### 구현할 기능 목록
- [O] 상품 목록 조회 시 카테고리 정보 포함
- [O] 카테고리 등록 시 카테고리 지정
- [O] 상품 수정시 카테고리 지정
- [O] 카테고리 목록 조회
- [O] 관리자 화면에서 상품 추가 시 카테고리 지정
- [O] 카테고리 삭제
- [0] 카테고리 수정

## Step2 - 상품 카테고리
### 기능 요구사항
상품 정보에 카테고리를 추가한다. 상품과 카테고리 모델 간의 관계를 고려하여 설계하고 구현한다.
- 상품에는 항상 하나의 카테고리가 있어야 한다.
- 상품 카테고리는 수정할 수 있다.
- 관리자 화면에서 상품을 추가할 때 카테고리를 지정할 수 있다.
- 카테고리는 1차 카테고리만 있으며 2차 카테고리는 고려하지 않는다.
- 카테고리의 예시는 아래와 같다.
  - 교환권, 상품권, 뷰티, 패션, 식품, 리빙/도서, 레저/스포츠, 아티스트/캐릭터, 유아동/반려, 디지털/가전, 카카오프렌즈, 트렌드 선물, 백화점, ...

### Request
```
GET /api/categories HTTP/1.1
Response
HTTP/1.1 200
```
```
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
### 카테고리 수정
PUT : http://localhost:8080/api/products/1/category
Params
Key: categoryName
Value: 교환권

### 상품 등록,수정
```
POST, PUT /api/prodcuts HTTP/1.1 , /api/prodcuts/1 HTTP/1.1
```
```
{
"name": "New Product",
"price": 20.00,
"imageUrl": "http://example.com/newproduct.jpg",
"description": "Description for new product",
"categoryName": "교환권",
"options": [
{
"name": "Option12",
"quantity": 100
}
]
}
```
## STEP3 - 옵션 수량 차감
### 기능 요구 사항 - 상품 옵션 빼기
1. 상품 옵션의 수량을 지정된 숫자만큼 빼는 기능 구현
2. 별도의 HTTP API를 만들 필요는 없음
3. 서비스 클래스 또는 엔티티 클래스에서 구현, 나중에 사용 가능하도록 함
