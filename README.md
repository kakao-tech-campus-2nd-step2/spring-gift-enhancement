# spring-gift-enhancement

## 1단계 기능 요구 사항
상품 정보에 카테고리를 추가한다. 상품과 카테고리 모델 간의 관계를 고려하여 설계하고 구현한다.

- 상품에는 항상 하나의 카테고리가 있어야 한다.  
- 상품 카테고리는 수정할 수 있다.  
- 관리자 화면에서 상품을 추가할 때 카테고리를 지정할 수 있다.  
- 카테고리는 1차 카테고리만 있으며 2차 카테고리는 고려하지 않는다.  
- 카테고리의 예시는 아래와 같다.  
- 교환권, 상품권, 뷰티, 패션, 식품, 리빙/도서, 레저/스포츠, 아티스트/캐릭터, 유아동/반려, 디지털/가전, 카카오프렌즈, 트렌드 선물, 백화점, ...  

아래 예시와 같이 HTTP 메시지를 주고받도록 구현한다.

### Request
```http request
GET /api/categories HTTP/1.1
```
### Response
```http request
HTTP/1.1 200
Content-Type: application/json
```
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

## 기능 구현 목록
- [x] 카테고리 도메인 설계
- [x] 상품과 연관 관계 설정
- [x] CategoryRepository 구현
- [x] CategoryService 구현
- [x] CategoryController 구현
  - 카테고리 생성
  - 카테고리 조회
  - 카테고리 수정
  - 카테고리 삭제

## 2단계 기능 요구 사항
상품 정보에 옵션을 추가한다. 상품과 옵션 모델 간의 관계를 고려하여 설계하고 구현한다.

- 상품에는 항상 하나 이상의 옵션이 있어야 한다.
- 옵션 이름은 공백을 포함하여 최대 50자까지 입력할 수 있다.
- 특수 문자
- 가능: ( ), [ ], +, -, &, /, _
- 그 외 특수 문자 사용 불가
- 옵션 수량은 최소 1개 이상 1억 개 미만이다.
- 중복된 옵션은 구매 시 고객에게 불편을 줄 수 있다. 동일한 상품 내의 옵션 이름은 중복될 수 없다.
- (선택) 관리자 화면에서 옵션을 추가할 수 있다.

아래 예시와 같이 HTTP 메시지를 주고받도록 구현한다.
### Request
```http request
GET /api/products/1/options HTTP/1.1
```
### Response
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